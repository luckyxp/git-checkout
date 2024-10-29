package com.xp.gitcheckout.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.xp.gitcheckout.BranchTree;
import com.xp.gitcheckout.settings.Branch;
import git4idea.branch.GitBrancher;
import git4idea.repo.GitRepository;
import git4idea.repo.GitRepositoryManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CheckoutAction extends AbstractTreeAction {
    private final GitRepositoryManager git;
    private final GitBrancher gitBrancher;

    public CheckoutAction(BranchTree tree) {
        super("Checkout", tree);
        git = GitRepositoryManager.getInstance(tree.getProject());
        gitBrancher = GitBrancher.getInstance(tree.getProject());
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        System.out.println("checkout");
        Branch branch = (Branch) selected(e)[0].getUserObject();
        Map<String, GitRepository> repos = git.getRepositories().stream().collect(Collectors.toMap((GitRepository repo) -> repo.getRoot().getName(), Function.identity()));
        for (Branch.Module module : branch.modules) {
            GitRepository repo = repos.get(module.name);
            gitBrancher.checkout(module.branch, false, Collections.singletonList(repo), null);
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        Presentation presentation = e.getPresentation();
        DefaultMutableTreeNode[] selected = selected(e);

        boolean visible = selected.length == 1 && selected[0].getLevel() == 1;
        presentation.setVisible(visible);
    }
}
