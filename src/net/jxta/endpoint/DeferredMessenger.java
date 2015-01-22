package net.jxta.endpoint;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import net.jxta.peergroup.PeerGroupID;
import net.jxta.util.SimpleSelectable;
import net.jxta.util.SimpleSelector;

public class DeferredMessenger implements Messenger
{
    private Messenger msgr;
    private boolean closed;
    private final String name;
    private List<Message> messages = new LinkedList<Message>();

    public DeferredMessenger(String name)
    {
        this.name = name;
    }

    public synchronized Messenger setMessenger(Messenger msgr)
    {
        if (msgr == null)
        {
            throw new IllegalArgumentException("Messenger can not be null");
        }
        if (this.msgr != null)
        {
            throw new IllegalArgumentException("Can not set the implementation twice");
        }
        this.msgr = msgr;
        if (closed)
        {
            this.msgr.close();
            return this;
        }
        //Leaving for Re-transmission to resolve any threading by re-transmission
        try
        {
            for (Message msg : messages)
            {
                sendMessageB(msg, null, null);
            }
            System.err.println("Resend " + messages.size() + " " + name);
        }
        catch (Exception e)
        {

            System.err.println("Unable to send deffered messages " + e);
            e.printStackTrace();
        }
        finally
        {
            messages.clear();
        }
        return this;
    }

    @Override
	public IdentityReference getIdentityReference()
    {
        throw new UnsupportedOperationException("getIdentityReference not implemented");
    }

    @Override
	public void register(SimpleSelector simpleSelector)
    {
        throw new UnsupportedOperationException("register not implemented"); 
    }

    @Override
	public void unregister(SimpleSelector simpleSelector)
    {
        throw new UnsupportedOperationException("unregister not implemented");
    }

    @Override
	public void itemChanged(SimpleSelectable simpleSelectable)
    {
        throw new UnsupportedOperationException("itemChanged not implemented");
    }

    @Override
	public int getState()
    {
        throw new UnsupportedOperationException("getState not implemented");
    }

    @Override
	public int waitState(int i, long l) throws InterruptedException
    {
        throw new UnsupportedOperationException("waitState not implemented");
    }

    @Override
	public void addStateListener(MessengerStateListener listener)
    {
        throw new UnsupportedOperationException("addStateListener not implemented");
    }

    @Override
	public void removeStateListener(MessengerStateListener listener)
    {
        throw new UnsupportedOperationException("removeStateListener not implemented");
    }

    @Override
	public boolean isClosed()
    {
        throw new UnsupportedOperationException("isClosed not implemented");
    }

    @Override
	public EndpointAddress getDestinationAddress()
    {
        throw new UnsupportedOperationException("getDestinationAddress not implemented");
    }

    @Override
	public EndpointAddress getLogicalDestinationAddress()
    {
        throw new UnsupportedOperationException("getLogicalDestinationAddress not implemented");
    }

    @Override
	public long getMTU()
    {
        throw new UnsupportedOperationException("getMTU not implemented");
    }

    @Override
	public Messenger getChannelMessenger(PeerGroupID peerGroupID, String s, String s1)
    {
        throw new UnsupportedOperationException("getChannelMessenger not implemented");
    }

    @Override
	public void close()
    {
        closed = true;
        if (this.msgr != null)
        {
            this.msgr.close();
        }
        messages.clear();
    }

    @Override
	public void flush() throws IOException
    {
        throw new UnsupportedOperationException("flush not implemented");
    }

    @Override
	public void resolve()
    {
        throw new UnsupportedOperationException("resolve not implemented");
    }

    @Override
	public void sendMessageB(Message message, String s, String s1) throws IOException
    {
        if (this.msgr == null)
        {
            if (closed)
            {
                throw new IOException("Messenger is closed");
            }
            System.err.println("Waiting for messenger to send message " + name);
            messages.add(message);
        }
        else
        {
            this.msgr.sendMessageB(message, s, s1);
        }

    }

    @Override
	public boolean sendMessageN(Message message, String s, String s1)
    {
        throw new UnsupportedOperationException("sendMessageN not implemented");
    }

    @Override
	public boolean sendMessage(Message message) throws IOException
    {
        throw new UnsupportedOperationException("sendMessage not implemented");
    }

    @Override
	public boolean sendMessage(Message message, String s, String s1) throws IOException
    {
        sendMessageB(message,s,s1);
        return true;
    }

    @Override
	public void sendMessage(Message message, String s, String s1, OutgoingMessageEventListener outgoingMessageEventListener)
    {
        throw new UnsupportedOperationException("sendMessage not implemented");
    }
}
