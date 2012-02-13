/*  
 * Copyright 2008 CoreMedia AG, Hamburg
 *
 * Licensed under the Apache License, Version 2.0 (the License); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0 
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an AS IS BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */

package com.coremedia.iso.boxes;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;

import java.io.IOException;
import java.nio.ByteBuffer;

public class SoundMediaHeaderBox extends AbstractMediaHeaderBox {

    public static final String TYPE = "smhd";
    private float balance;

    public SoundMediaHeaderBox() {
        super(TYPE);
    }

    public float getBalance() {
        return balance;
    }

    protected long getContentSize() {
        return 8;
    }

    @Override
    public void _parseDetails() {
        parseVersionAndFlags();
        balance = IsoTypeReader.readFixedPoint88(content);
        IsoTypeReader.readUInt16(content);
    }

    @Override
    protected void getContent(ByteBuffer bb) throws IOException {
        writeVersionAndFlags(bb);
        IsoTypeWriter.writeFixedPont88(bb, balance);
        IsoTypeWriter.writeUInt16(bb, 0);
    }

    public String toString() {
        return "SoundMediaHeaderBox[balance=" + getBalance() + "]";
    }
}
