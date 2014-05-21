/*
 * Copyright (C) 2014 json
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package skygge;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author json
 */
public class SoundDeviceManagerTest {
    
    public SoundDeviceManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getInstance method, of class SoundDeviceManager.
     */
    @Test
    public void testGetSoundDeviceManagerInstance() {
        System.out.println("getSoundDeviceManagerInstance");
        SoundDeviceManager expResult = null;
        SoundDeviceManager result = SoundDeviceManager.getInstance();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startPlaying method, of class SoundDeviceManager.
     */
    @Test
    public void testStartPlaying() {
        System.out.println("startPlaying");
        byte[] audioDataToBePlayed = null;
        SoundDeviceManager instance = new SoundDeviceManager();
        instance.startPlaying(audioDataToBePlayed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startLooping method, of class SoundDeviceManager.
     */
    @Test
    public void testStartLooping() {
        System.out.println("startLooping");
        byte[] audioDataToBePlayed = null;
        SoundDeviceManager instance = new SoundDeviceManager();
        instance.startLooping(audioDataToBePlayed);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of startRecording method, of class SoundDeviceManager.
     */
    @Test
    public void testStartRecording() {
        System.out.println("startRecording");
        SoundDeviceManager instance = new SoundDeviceManager();
        instance.startRecording();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of stopEverything method, of class SoundDeviceManager.
     */
    @Test
    public void testStopEverything() {
        System.out.println("stopEverything");
        SoundDeviceManager instance = new SoundDeviceManager();
        instance.stopEverything();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRecordedAudioData method, of class SoundDeviceManager.
     */
    @Test
    public void testGetRecordedAudioData() {
        System.out.println("getRecordedAudioData");
        SoundDeviceManager instance = new SoundDeviceManager();
        byte[] expResult = null;
        byte[] result = instance.getRecordedAudioData();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of run method, of class SoundDeviceManager.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        SoundDeviceManager instance = new SoundDeviceManager();
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
