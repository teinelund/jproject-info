package com.teinelund.jproject_info.main_argument_parser;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import picocli.CommandLine;

import java.io.FileReader;
import java.io.IOException;

class VersionProvider implements CommandLine.IVersionProvider {
    public String[] getVersion() {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = null;
        try {
            model = reader.read(new FileReader("pom.xml"));
        } catch (IOException | XmlPullParserException e) {
            e.printStackTrace();
            return new String[]{"Could not fetch version from application."};
        }
        return new String[]{"@|white Version: " + model.getVersion() + "|@", "@|yellow Copyright (C) Teinelund 2020.|@"};
    }
}
