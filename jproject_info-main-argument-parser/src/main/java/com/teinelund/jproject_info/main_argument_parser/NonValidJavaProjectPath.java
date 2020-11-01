package com.teinelund.jproject_info.main_argument_parser;

import java.nio.file.Path;

public class NonValidJavaProjectPath {
    public NonValidJavaProjectPath(NonValidJavaProjectPath.Builder builder) {
        this.javaProjectPath = builder.javaProjectPath;
        this.index = builder.index;
        this.errorString = builder.errorString;
    }

    private Path javaProjectPath;
    private int index;
    private String errorString;

    public Path getJavaProjectPath() {
        return this.javaProjectPath;
    }

    public int getIndex() {
        return this.index;
    }

    public String getErrorString() {
        return this.errorString;
    }

    public static class Builder {
        private Path javaProjectPath;
        private int index;
        private String errorString;

        public Builder setJavaProjectPath( Path javaProjectPath ) {
            this.javaProjectPath = javaProjectPath;
            return this;
        }

        public Builder setIndex( int index ) {
            this.index = index;
            return this;
        }

        public Builder setErrorString( String errorString ) {
            this.errorString = errorString;
            return this;
        }

        public NonValidJavaProjectPath build() {
            return new NonValidJavaProjectPath( this );
        }
    }
}
