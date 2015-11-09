package org.YiiCommunity.GitterBot.containers;

import org.YiiCommunity.GitterBot.GitterBot;

import java.io.IOException;

public class GitHub {
    public static org.kohsuke.github.GitHub github;

    public static org.kohsuke.github.GitHub getClient() {
        if (github == null)
            try {
                github = org.kohsuke.github.GitHub.connectUsingOAuth(GitterBot.getInstance().getConfiguration().getGitHubToken());
            } catch (IOException e) {
                e.printStackTrace();
            }
        return github;
    }
}
