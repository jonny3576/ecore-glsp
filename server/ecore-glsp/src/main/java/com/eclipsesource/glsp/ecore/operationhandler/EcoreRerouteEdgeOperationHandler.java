/********************************************************************************
 * Copyright (c) 2019 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 ********************************************************************************/
package com.eclipsesource.glsp.ecore.operationhandler;

import java.util.List;

import com.eclipsesource.glsp.api.action.kind.AbstractOperationAction;
import com.eclipsesource.glsp.api.action.kind.RerouteConnectionOperationAction;
import com.eclipsesource.glsp.api.handler.OperationHandler;
import com.eclipsesource.glsp.api.model.GraphicalModelState;
import com.eclipsesource.glsp.ecore.EcoreModelIndex;
import com.eclipsesource.glsp.ecore.enotation.Edge;
import com.eclipsesource.glsp.ecore.model.EcoreModelState;
import com.eclipsesource.glsp.graph.GPoint;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;

public class EcoreRerouteEdgeOperationHandler implements OperationHandler {

    public EcoreRerouteEdgeOperationHandler() {

    }

    @Override
    public Class<?> handlesActionType() {
        return RerouteConnectionOperationAction.class;
    }

    @Override
    public void execute(AbstractOperationAction action, GraphicalModelState modelState) {
        EcoreModelIndex index = EcoreModelState.getModelState(modelState).getIndex();
        RerouteConnectionOperationAction rerouteAction = (RerouteConnectionOperationAction) action;
        rerouteEdge(rerouteAction, index);
    }

    private void rerouteEdge(RerouteConnectionOperationAction action, EcoreModelIndex index) {
        index.getNotation(action.getConnectionElementId(), Edge.class)
                .ifPresent(notationElement -> changeEdgePoints(notationElement, action.getRoutingPoints()));
    }

    private void changeEdgePoints(Edge element, List<GPoint> points) {
        EList<GPoint> ePoints = new BasicEList<GPoint>(points);
        if(points != null) element.setBendPoints(ePoints);
    }

    @Override
    public String getLabel(AbstractOperationAction action) {
        return "Reroute ecore edge";
    }
}