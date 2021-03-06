/**
 * Licensed to Neo Technology under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Neo Technology licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.neo4j.neoclipse.editor;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.neoclipse.util.ApplicationUtil;

public class NodeWrapper extends BaseWrapper
{

    private static final long serialVersionUID = 1L;


    private List<RelationshipWrapper> relation = new ArrayList<RelationshipWrapper>();

    public NodeWrapper()
    {
    }


    public NodeWrapper( long id )
    {
        super( id );
    }

    public List<RelationshipWrapper> getRelation()
    {
        return relation;
    }


    public void setRelation( List<RelationshipWrapper> relation )
    {
        this.relation = relation;
    }

    public void addRelation( RelationshipWrapper relation )
    {
        this.relation.add( relation );
    }


    @Override
    public String toString()
    {
        return ApplicationUtil.toJson( this );
    }

}
