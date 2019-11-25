package com.eclipsesource.glsp;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

public class EcoreProvider {
    public static void main(String[] args) throws IOException
    {
		String name = null;
		String prefix = null;
		String uri = null;
		Path workspacePath = null;
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			switch (arg) {
			case "-name":
				i++;
				name = args[i];
				break;
			case "-prefix":
				i++;
				prefix = args[i];
				break;
			case "-uri":
				i++;
				uri = args[i];
				break;
			case "-workspacePath":
				i++;
				workspacePath = Paths.get(args[i], name+".ecore");
				break;
			}
		}

		if (name == null) {
			System.exit(-10);
		}
		if (prefix == null) {
			System.exit(-11);
		}
		if (uri == null) {
			System.exit(-12);
		}
		if (workspacePath == null) {
			System.exit(-13);
		}


        ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		XMLResource resource = (XMLResource) resourceSet.createResource(URI.createFileURI(workspacePath.toString()));
		EPackage ePackage = EcoreFactory.eINSTANCE.createEPackage();
		ePackage.setName(name);
		ePackage.setNsPrefix(prefix);
		ePackage.setNsURI(uri);
		resource.setEncoding("UTF-8");
		resource.getContents().add(ePackage);
		resource.save(null);
    }
}
