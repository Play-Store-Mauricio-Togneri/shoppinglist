package com.mauriciotogneri.common.wearable;

import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.utils.Serializer;

import java.util.ArrayList;

public class WearableApi
{
    public static final class Paths
    {
        public static final String GET_CART = "/get_cart";
        public static final String RESULT_CART = "/result_cart";

        public static final String MARK_CART_ELEMENT = "/mark_cart_element";
    }

    public static final class Messages
    {
        public static Message getCart(String nodeId)
        {
            return new Message(nodeId, Paths.GET_CART);
        }

        public static Message resultCart(String nodeId, ArrayList<CartElement> elements)
        {
            String aaa = Serializer.serialize(elements);

            ArrayList<CartElement> bb = Serializer.deserialize(aaa);

            return new Message(nodeId, Paths.RESULT_CART, aaa);
        }

        public static Message markCartElement(String nodeId, CartElement cartElement)
        {
            return new Message(nodeId, Paths.MARK_CART_ELEMENT, Serializer.serialize(cartElement));
        }
    }
}