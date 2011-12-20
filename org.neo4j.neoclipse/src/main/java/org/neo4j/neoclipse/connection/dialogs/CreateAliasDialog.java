package org.neo4j.neoclipse.connection.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.neo4j.neoclipse.Activator;
import org.neo4j.neoclipse.connection.Alias;
import org.neo4j.neoclipse.graphdb.GraphDbServiceMode;
import org.neo4j.neoclipse.preference.Preferences;

/**
 * @author Radhakrishna Kalyan
 * 
 */
public class CreateAliasDialog extends TitleAreaDialog
{

    private static final int SIZING_TEXT_FIELD_WIDTH = 250;

    public enum Type
    {
        CREATE,
        CHANGE,
        COPY
    }

    private Type type;

    private Text nameField;
    private DirectoryFieldEditor dbLocationField;
    private Text userField;
    private Text passwordField;

    public CreateAliasDialog( Shell parentShell, Type type )
    {
        super( parentShell );
        this.type = type;
    }

    @Override
    protected void configureShell( Shell shell )
    {

        super.configureShell( shell );
        if ( type == Type.CREATE )
        {
            shell.setText( "Create new connection" );
        }
        else if ( type == Type.CHANGE )
        {
            shell.setText( "Change connection" );
        }
        else if ( type == Type.COPY )
        {
            shell.setText( "AliasDialog.Copy.Title" );
        }
    }

    @Override
    protected void createButtonsForButtonBar( Composite parent )
    {

        super.createButtonsForButtonBar( parent );
        validate();
    }

    @Override
    protected Control createContents( Composite parent )
    {

        Control contents = super.createContents( parent );

        if ( type == Type.CREATE )
        {
            setTitle( "Create new connection" );
        }
        else if ( type == Type.CHANGE )
        {
            setTitle( "Change connection" );
            setMessage( "AliasDialog.Change.SubTitle" );
        }
        else if ( type == Type.COPY )
        {
            setTitle( "AliasDialog.Copy.Title" );
            setMessage( "AliasDialog.Copy.SubTitle" );
        }

        return contents;
    }

    @Override
    protected Control createDialogArea( Composite parent )
    {

        // top level composite
        Composite parentComposite = (Composite) super.createDialogArea( parent );

        // create a composite with standard margins and spacing
        Composite composite = new Composite( parentComposite, SWT.NONE );
        GridLayout layout = new GridLayout();
        layout.marginHeight = convertVerticalDLUsToPixels( IDialogConstants.VERTICAL_MARGIN );
        layout.marginWidth = convertHorizontalDLUsToPixels( IDialogConstants.HORIZONTAL_MARGIN );
        layout.verticalSpacing = convertVerticalDLUsToPixels( IDialogConstants.VERTICAL_SPACING );
        layout.horizontalSpacing = convertHorizontalDLUsToPixels( IDialogConstants.HORIZONTAL_SPACING );
        composite.setLayout( layout );
        composite.setLayoutData( new GridData( GridData.FILL_BOTH ) );
        composite.setFont( parentComposite.getFont() );

        Composite nameGroup = new Composite( composite, SWT.NONE );
        layout = new GridLayout();
        layout.numColumns = 3;
        layout.marginWidth = 10;
        nameGroup.setLayout( layout );
        GridData data = new GridData( SWT.FILL, SWT.CENTER, true, false );
        nameGroup.setLayoutData( data );

        Label label = new Label( nameGroup, SWT.WRAP );
        label.setText( ( "Name *" ) );
        nameField = new Text( nameGroup, SWT.BORDER );
        data = new GridData( GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL );
        data.horizontalSpan = 2;
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        nameField.setLayoutData( data );
        nameField.addKeyListener( new KeyListener()
        {

            @Override
            public void keyPressed( org.eclipse.swt.events.KeyEvent e )
            {

                CreateAliasDialog.this.validate();
            };

            @Override
            public void keyReleased( org.eclipse.swt.events.KeyEvent e )
            {

                CreateAliasDialog.this.validate();
            };
        } );

        dbLocationField = new DirectoryFieldEditor( Preferences.DATABASE_LOCATION, "Directory Location *", nameGroup );
        dbLocationField.getTextControl( nameGroup ).addKeyListener( new KeyListener()
        {

            @Override
            public void keyReleased( KeyEvent arg0 )
            {
                CreateAliasDialog.this.validate();
            }

            @Override
            public void keyPressed( KeyEvent arg0 )
            {
                CreateAliasDialog.this.validate();
            }
        } );
        dbLocationField.setPropertyChangeListener( new IPropertyChangeListener()
        {

            @Override
            public void propertyChange( PropertyChangeEvent event )
            {
                CreateAliasDialog.this.validate();

            }
        } );

        new Label( nameGroup, SWT.NONE );
        Label label3 = new Label( nameGroup, SWT.WRAP );
        label3.setText( ( "i.e http://<host>:<port>/<db> or C:/neo4j/db " ) );
        data = new GridData( GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL );
        data.horizontalSpan = 2;
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        label3.setLayoutData( data );

        new Label( nameGroup, SWT.NONE );

        Composite connectionPropertiesComposite = new Composite( nameGroup, SWT.NONE );
        connectionPropertiesComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false ) );
        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.marginWidth = 0;
        connectionPropertiesComposite.setLayout( gridLayout );
        data = new GridData( GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL );
        data.horizontalSpan = 2;
        connectionPropertiesComposite.setLayoutData( data );

        Label label4 = new Label( nameGroup, SWT.WRAP );
        label4.setText( ( "User" ) );
        userField = new Text( nameGroup, SWT.BORDER );
        data = new GridData( GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL );
        data.horizontalSpan = 2;
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        userField.setLayoutData( data );

        Label label5 = new Label( nameGroup, SWT.WRAP );
        label5.setText( ( "Password" ) );
        passwordField = new Text( nameGroup, SWT.BORDER );
        passwordField.setEchoChar( '*' );
        data = new GridData( GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL );
        data.horizontalSpan = 2;
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        passwordField.setLayoutData( data );

        new Label( nameGroup, SWT.NONE );

        connectionPropertiesComposite = new Composite( nameGroup, SWT.NONE );
        connectionPropertiesComposite.setLayoutData( new GridData( SWT.FILL, SWT.CENTER, false, false ) );
        gridLayout = new GridLayout();
        gridLayout.numColumns = 2;
        gridLayout.marginWidth = 0;
        connectionPropertiesComposite.setLayout( gridLayout );
        data = new GridData( GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL );
        data.horizontalSpan = 2;
        connectionPropertiesComposite.setLayoutData( data );

        return parentComposite;
    }

    @Override
    protected void okPressed()
    {

        String connectionMode = Activator.getDefault().getPreferenceStore().getString( Preferences.CONNECTION_MODE );
        Alias connection;

        connection = new Alias( nameField.getText(), dbLocationField.getStringValue(),
                GraphDbServiceMode.getValue( connectionMode ) );
        Activator.getDefault().getAliasManager().addConnection( connection );
        Activator.getDefault().getAliasManager().modelChanged();
        close();

    }

    private void setDialogComplete( boolean value )
    {

        Button okBtn = getButton( IDialogConstants.OK_ID );
        if ( okBtn != null )
        {
            okBtn.setEnabled( value );
        }
    }

    @Override
    protected void setShellStyle( int newShellStyle )
    {

        super.setShellStyle( newShellStyle | SWT.RESIZE );// Make the dialog
        // resizable
    }

    private void validate()
    {

        if ( !dbLocationField.getStringValue().trim().isEmpty() && ( nameField.getText().trim().length() > 0 ) )
        {
            setDialogComplete( true );
        }
        else
        {
            setDialogComplete( false );
        }
    }

}