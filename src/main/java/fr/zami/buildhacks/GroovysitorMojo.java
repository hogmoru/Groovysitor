package fr.zami.buildhacks;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

@Mojo(name = "groovysitor", defaultPhase = LifecyclePhase.PROCESS_RESOURCES,
        requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME,
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME)
public class GroovysitorMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject mavenProject;

    @Parameter(property="script", required = true)
    private String script;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Binding binding = new Binding();
        binding.setVariable("project", mavenProject);
        try {
            Object result = new GroovyShell(binding).evaluate(new File(script));
            getLog().info("Result:\n" + result);
        } catch (IOException e) {
            throw new MojoExecutionException("Failed to execute script '" + script + "'", e);
        }
    }

}