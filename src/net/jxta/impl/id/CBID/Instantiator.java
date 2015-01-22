/*
 * Copyright (c) 2001-2007 Sun Microsystems, Inc.  All rights reserved.
 *
 *  The Sun Project JXTA(TM) Software License
 *
 *  Redistribution and use in source and binary forms, with or without 
 *  modification, are permitted provided that the following conditions are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice, 
 *     this list of conditions and the following disclaimer in the documentation 
 *     and/or other materials provided with the distribution.
 *
 *  3. The end-user documentation included with the redistribution, if any, must 
 *     include the following acknowledgment: "This product includes software 
 *     developed by Sun Microsystems, Inc. for JXTA(TM) technology." 
 *     Alternately, this acknowledgment may appear in the software itself, if 
 *     and wherever such third-party acknowledgments normally appear.
 *
 *  4. The names "Sun", "Sun Microsystems, Inc.", "JXTA" and "Project JXTA" must 
 *     not be used to endorse or promote products derived from this software 
 *     without prior written permission. For written permission, please contact 
 *     Project JXTA at http://www.jxta.org.
 *
 *  5. Products derived from this software may not be called "JXTA", nor may 
 *     "JXTA" appear in their name, without prior written permission of Sun.
 *
 *  THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES,
 *  INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND 
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL SUN 
 *  MICROSYSTEMS OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, 
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT 
 *  LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, 
 *  OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF 
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING 
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 *  EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  JXTA is a registered trademark of Sun Microsystems, Inc. in the United 
 *  States and other countries.
 *
 *  Please see the license information page at :
 *  <http://www.jxta.org/project/www/license.html> for instructions on use of 
 *  the license in source files.
 *
 *  ====================================================================
 *
 *  This software consists of voluntary contributions made by many individuals 
 *  on behalf of Project JXTA. For more information on Project JXTA, please see 
 *  http://www.jxta.org.
 *
 *  This license is based on the BSD license adopted by the Apache Foundation. 
 */

package net.jxta.impl.id.CBID;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Random;

import net.jxta.impl.id.UUID.IDBytes;

/**
 * The instantiator for the CBID ID Format.
 * <p/>
 * <p/>For "seed" variant constructors, the "seed" must be a certificate.
 */
public class Instantiator implements net.jxta.id.IDFactory.Instantiator {

	/**
     * Our ID Format
     */
    final static String CBIDEncoded = "cbid";
    

    /**
     * {@inheritDoc}
     */
    @Override
	public String getSupportedIDFormat() {
        return CBIDEncoded;
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.codat.CodatID newCodatID(net.jxta.peergroup.PeerGroupID groupID) {
        PeerGroupID peerGroupID = (PeerGroupID) IDFormat.translateFromWellKnown(groupID);

        return new CodatID(peerGroupID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.codat.CodatID newCodatID(net.jxta.peergroup.PeerGroupID groupID, byte[] seed) {
        PeerGroupID peerGroupID = (PeerGroupID) IDFormat.translateFromWellKnown(groupID);

        return new CodatID(peerGroupID, seed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.codat.CodatID newCodatID(net.jxta.peergroup.PeerGroupID groupID, InputStream in) throws IOException {
        PeerGroupID peerGroupID = (PeerGroupID) IDFormat.translateFromWellKnown(groupID);

        return new CodatID(peerGroupID, in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.codat.CodatID newCodatID(net.jxta.peergroup.PeerGroupID groupID, byte[] seed, InputStream in) throws IOException {
        PeerGroupID peerGroupID = (PeerGroupID) IDFormat.translateFromWellKnown(groupID);

        return new CodatID(peerGroupID, seed, in);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.content.ContentID newContentID(
            net.jxta.peergroup.PeerGroupID groupID, boolean contentIsStatic) {
        PeerGroupID  peerGroupID =
                (PeerGroupID) IDFormat.translateFromWellKnown( groupID  );
        return new ContentID( peerGroupID, contentIsStatic );
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.content.ContentID newContentID(
            net.jxta.peergroup.PeerGroupID groupID, boolean contentIsStatic,
            byte[] indexSeed) {
        PeerGroupID  peerGroupID =
                (PeerGroupID) IDFormat.translateFromWellKnown( groupID );
        return new ContentID( peerGroupID, contentIsStatic, indexSeed );
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.content.ContentID newContentID(
            net.jxta.peergroup.PeerGroupID groupID, boolean contentIsStatic,
            InputStream indexSeed )
            throws IOException {
        PeerGroupID  peerGroupID =
                (PeerGroupID) IDFormat.translateFromWellKnown( groupID );
        return new ContentID( peerGroupID, contentIsStatic, indexSeed );
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.content.ContentID newContentID(
            net.jxta.peergroup.PeerGroupID groupID, boolean contentIsStatic,
            byte[] indexSeed, byte[] variant) {
        PeerGroupID  peerGroupID =
                (PeerGroupID) IDFormat.translateFromWellKnown( groupID );
        return new ContentID( peerGroupID, contentIsStatic, indexSeed, variant );
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.content.ContentID newContentID(
            net.jxta.peergroup.PeerGroupID groupID, boolean contentIsStatic,
            byte[] indexSeed, InputStream variant )
            throws IOException {
        PeerGroupID  peerGroupID =
                (PeerGroupID) IDFormat.translateFromWellKnown( groupID );
        return new ContentID( peerGroupID, contentIsStatic, indexSeed, variant );
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.content.ContentID newContentID(
            net.jxta.peergroup.PeerGroupID groupID, boolean contentIsStatic,
            InputStream indexSeed, byte[] variant)
    throws IOException {
        PeerGroupID  peerGroupID =
                (PeerGroupID) IDFormat.translateFromWellKnown( groupID );
        return new ContentID( peerGroupID, contentIsStatic, indexSeed, variant );
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.content.ContentID newContentID(
            net.jxta.peergroup.PeerGroupID groupID, boolean contentIsStatic,
            InputStream indexSeed, InputStream variant )
            throws IOException {
        PeerGroupID  peerGroupID =
                (PeerGroupID) IDFormat.translateFromWellKnown( groupID );
        return new ContentID( peerGroupID, contentIsStatic, indexSeed, variant );
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.peergroup.PeerGroupID newPeerGroupID() {
        return new PeerGroupID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.peergroup.PeerGroupID newPeerGroupID(byte[] seed) {
        return new PeerGroupID(seed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.peergroup.PeerGroupID newPeerGroupID(net.jxta.peergroup.PeerGroupID parent) {
        PeerGroupID  parentGroupID = (PeerGroupID) IDFormat.translateFromWellKnown(parent);
        return new PeerGroupID(parentGroupID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.peergroup.PeerGroupID newPeerGroupID(net.jxta.peergroup.PeerGroupID parent, byte[] seed) {
        PeerGroupID parentGroupID = (PeerGroupID) IDFormat.translateFromWellKnown(parent);

        return new PeerGroupID(parentGroupID, seed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.peer.PeerID newPeerID(net.jxta.peergroup.PeerGroupID groupID) {
        return newPeerID(groupID, generateRandomSeed());
    }

    private byte[] generateRandomSeed() {
		Random r = new Random();
		byte[] seed = new byte[4];
		r.nextBytes(seed);
		return seed;
	}

	/**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.peer.PeerID newPeerID(net.jxta.peergroup.PeerGroupID groupID, byte[] seed) {
        PeerGroupID peerGroupID = (PeerGroupID) IDFormat.translateFromWellKnown(groupID);

        return new PeerID(peerGroupID, seed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.pipe.PipeID newPipeID(net.jxta.peergroup.PeerGroupID groupID) {
        PeerGroupID peerGroupID = (PeerGroupID) IDFormat.translateFromWellKnown(groupID);

        return new PipeID(peerGroupID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.pipe.PipeID newPipeID(net.jxta.peergroup.PeerGroupID groupID, byte[] seed) {
        PeerGroupID peerGroupID = (PeerGroupID) IDFormat.translateFromWellKnown(groupID);

        return new PipeID(peerGroupID, seed);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.platform.ModuleClassID newModuleClassID() {
        return new ModuleClassID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.platform.ModuleClassID newModuleClassID(net.jxta.platform.ModuleClassID classID) {
        return new ModuleClassID((ModuleClassID) classID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.platform.ModuleSpecID newModuleSpecID(net.jxta.platform.ModuleClassID classID) {
        return new ModuleSpecID((ModuleClassID) classID);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.id.ID fromURI(URI source) throws URISyntaxException {

        // check the protocol
        if (!net.jxta.id.ID.URIEncodingName.equalsIgnoreCase(source.getScheme())) {
            throw new URISyntaxException(source.toString(), "URI scheme was not as expected.");
        }

        String decoded = source.getSchemeSpecificPart();

        int colonAt = decoded.indexOf(':');

        // There's a colon right?
        if (-1 == colonAt) {
            throw new URISyntaxException(source.toString(), "URN namespace was missing.");
        }

        // check the namespace
        if (!net.jxta.id.ID.URNNamespace.equalsIgnoreCase(decoded.substring(0, colonAt))) {
            throw new URISyntaxException(source.toString()
                    ,
                    "URN namespace was not as expected. (" + net.jxta.id.ID.URNNamespace + "!=" + decoded.substring(0, colonAt)
                    + ")");
        }

        // skip the namespace portion and the colon
        decoded = decoded.substring(colonAt + 1);

        return fromURNNamespaceSpecificPart(decoded);
    }

    /**
     * {@inheritDoc}
     */
    @Override
	public net.jxta.id.ID fromURNNamespaceSpecificPart(String source) throws URISyntaxException {
        int dashAt = source.indexOf('-');

        // there's a dash, right?
        if (-1 == dashAt) {
            throw new URISyntaxException(source, "URN Encodingtype was missing.");
        }

        if (!source.substring(0, dashAt).equals(getSupportedIDFormat())) {
            throw new URISyntaxException(source, "JXTA ID Format was not as expected.");
        }

        // skip the dash
        source = source.substring(dashAt + 1);

        // check that the length is even
        if (0 != (source.length() % 2)) {
            throw new URISyntaxException(source, "URN contains an odd number of chars");
        }

        // check that the length is long enough
        if (source.length() < 2) {
            throw new URISyntaxException(source, "URN does not contain enough chars");
        }

        // check that id is short enough
        if (net.jxta.impl.id.UUID.IDFormat.IdByteArraySize < (source.length() % 2)) {
            throw new URISyntaxException(source, "URN contains too many chars");
        }

        net.jxta.id.ID result = null;
        IDBytes id = new IDBytes();

        try {
            // do the primary portion.
            for (int eachByte = 0; eachByte < ((source.length() / 2) - net.jxta.impl.id.UUID.IDFormat.flagsSize); eachByte++) {
                int index = eachByte * 2;
                String twoChars = source.substring(index, index + 2);

                id.bytes[eachByte] = (byte) Integer.parseInt(twoChars, 16);
            }

            // do the flags
            for (int eachByte = net.jxta.impl.id.UUID.IDFormat.flagsOffset; eachByte < net.jxta.impl.id.UUID.IDFormat.IdByteArraySize; eachByte++) {
                int index = source.length() - (net.jxta.impl.id.UUID.IDFormat.IdByteArraySize - eachByte) * 2;
                String twoChars = source.substring(index, index + 2);

                id.bytes[eachByte] = (byte) Integer.parseInt(twoChars, 16);
            }
        } catch (NumberFormatException caught) {
            throw new URISyntaxException(source, "Invalid Character in JXTA URI");
        }

        switch (id.bytes[net.jxta.impl.id.UUID.IDFormat.flagsOffset + net.jxta.impl.id.UUID.IDFormat.flagsIdTypeOffset]) {
        case net.jxta.impl.id.UUID.IDFormat.flagCodatID:
            result = new CodatID(id);
            break;

        case net.jxta.impl.id.UUID.IDFormat.flagPeerGroupID:
            result = new PeerGroupID(id);
            result = IDFormat.translateToWellKnown(result);
            break;

        case net.jxta.impl.id.UUID.IDFormat.flagPeerID:
            result = new PeerID(id);
            break;

        case net.jxta.impl.id.UUID.IDFormat.flagPipeID:
            result = new PipeID(id);
            break;

        case net.jxta.impl.id.UUID.IDFormat.flagModuleClassID:
            result = new ModuleClassID(id);
            break;

        case net.jxta.impl.id.UUID.IDFormat.flagModuleSpecID:
            result = new ModuleSpecID(id);
            break;

        case net.jxta.impl.id.UUID.IDFormat.flagContentID:
            result = new ContentID(id);
            break;

        default:
            throw new URISyntaxException(source, "JXTA ID Type not recognized");
        }
        return result;
    }
}
