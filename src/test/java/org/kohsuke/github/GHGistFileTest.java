package org.kohsuke.github;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
/**
 * Tests corresponding to the <a href="https://github.com/hub4j/github-api/issues/1279">Issue#1279</a>.
 *
 * @author Xuguang Wu
 */
public class GHGistFileTest extends AbstractGitHubWireMockTest {
    @Test
    public void truncateTest() throws Exception {
        /**
         * set up the 1MB data to test the truncate feature
         * CS427 Issue link: https://github.com/hub4j/github-api/issues/1279
         *
         */
        char[] data = new char[1000000];
        String strLess1MB = new String(data);
        /**
         * Adeed 5 char in the test string, these value should be truncated
         */
        String strGreater1MB = strLess1MB + "abcde";
        // CRUD operation
        /**
         * Start to run the github api and test the truncate feature.
         */
        GHGist gist = gitHub.createGist()
                .public_(false)
                .description("Test Truncate")
                .file("greater1MB.txt", strGreater1MB)
                .create();

        /**
         * test result for the new implemented feature.
         */
        assertThat(gist.getFile("greater1MB.txt").getContent(), equalTo(strLess1MB));

        String id = gist.getGistId();

        GHGist gistUpdate = gitHub.getGist(id);

        gistUpdate.update().description("Test Truncate").deleteFile("greater1MB.txt").update();
    }
}
