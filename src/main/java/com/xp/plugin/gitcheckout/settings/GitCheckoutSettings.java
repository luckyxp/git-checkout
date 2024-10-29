package com.xp.plugin.gitcheckout.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "GitCheckoutSettings", storages = @Storage("git-checkout.xml"))
@Service(Service.Level.PROJECT)
public final class GitCheckoutSettings implements PersistentStateComponent<GitCheckoutState> {
    private GitCheckoutState state;

    @Override
    public @Nullable GitCheckoutState getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull GitCheckoutState state) {
        this.state = state;
    }

    @Override
    public void noStateLoaded() {
        this.state = new GitCheckoutState();
    }

    public static GitCheckoutState settings(Project project) {
        return project.getService(GitCheckoutSettings.class).getState();
    }
}
