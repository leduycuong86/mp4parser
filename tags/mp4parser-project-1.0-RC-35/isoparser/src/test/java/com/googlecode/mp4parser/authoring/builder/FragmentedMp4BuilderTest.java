package com.googlecode.mp4parser.authoring.builder;

import com.coremedia.iso.Hex;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.BoxComparator;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.Channels;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by sannies on 1/9/14.
 */
public class FragmentedMp4BuilderTest {

    /**
     * This test indicated that you changed the output. Do you expect that?
     */
    @Test
    public void stabilize() throws IOException, NoSuchAlgorithmException {
        Movie movie = MovieCreator.build(this.getClass().getProtectionDomain().getCodeSource().getLocation().getFile() +
                "/Beethoven - Bagatelle op.119 no.11 i.m4a");
        FragmentedMp4Builder mp4Builder = new FragmentedMp4Builder() {
            @Override
            public Date getDate() {
                return new Date(0);
            }
        };
        mp4Builder.setIntersectionFinder(new TwoSecondIntersectionFinder(movie, 2));
        Container fMp4 = mp4Builder.build(movie);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        fMp4.writeContainer(Channels.newChannel(baos));
        MessageDigest md = MessageDigest.getInstance("MD5");
        String digest = Hex.encodeHex(md.digest(baos.toByteArray()));
        System.err.println(digest);
        String oldDigest = "1329F9A3D6CF44A7061979379798692A";

        Assert.assertEquals(oldDigest, digest);

    }

}
