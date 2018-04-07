package com.mauriciotogneri.common.utils;

import com.mauriciotogneri.javautils.Resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializer
{
    public static byte[] serialize(Serializable object)
    {
        ByteArrayOutputStream bos = null;
        ObjectOutput out = null;

        try
        {
            bos = new ByteArrayOutputStream();
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();

            return bos.toByteArray();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (Exception e)
            {
                // ignore
            }

            Resource.close(bos);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserialize(byte[] data)
    {
        ByteArrayInputStream bis = null;
        ObjectInput in = null;

        try
        {
            bis = new ByteArrayInputStream(data);
            in = new ObjectInputStream(bis);

            return (T) in.readObject();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (in != null)
                {
                    in.close();
                }
            }
            catch (Exception e)
            {
                // ignore
            }

            Resource.close(bis);
        }

        return null;
    }
}