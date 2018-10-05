/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stpl.gtn.gtn2o;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.maven.project.MavenProject;

/**
 *
 * @author abishekram.r
 */
public class FileCopier {

	public void copyFile(MavenProject project) {
		File projectFolder = project.getBasedir();
		FilenameFilter fr = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.equalsIgnoreCase("target");
			}
		};
		FilenameFilter jarr = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.lastIndexOf('.') > 0) {
					// get last index for '.' char
					int lastIndex = name.lastIndexOf('.');

					// get extension
					String str = name.substring(lastIndex);

					// match path name extension
					if (str.equals(".jar") || str.equals(".war")) {
						return true;
					}
				}
				return false;
			}

		};

		for (File aan : projectFolder.listFiles(fr)) {
			if (aan.isDirectory()) {
				for (File aasn : aan.listFiles(jarr)) {
					copyTo(aasn, project);
				}
			}

		}

	}

	private void copyTo(File aasn, MavenProject project) {
		File copy = new File(getParentFile(project) + "/Dist");
		if (!copy.isDirectory()) {
			copy.mkdir();
		}

		try {
			FileUtils.copyFile(aasn, new File(copy.getPath() + "/" + aasn.getName()));
		} catch (IOException ex) {
			Logger.getLogger(FileCopier.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private String getParentFile(MavenProject project) {
		MavenProject parent = project.getParent();
		if (parent.getParent() == null) {
			return parent.getBasedir().getPath();
		} else {
			return getParentFile(parent);
		}
	}

}