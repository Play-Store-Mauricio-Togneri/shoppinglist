package com.mauriciotogneri.common.message;

import com.mauriciotogneri.common.api.CartElement;
import com.mauriciotogneri.common.utils.Serializer;

import java.util.ArrayList;

public class MessageApi
{
    public static final String ACTION_UPDATE_LIST = "update_list";

    public static final class Paths
    {
        public static final String GET_CART = "/get_cart";
        public static final String RESULT_CART = "/result_cart";

        public static final String MARK_CART_ELEMENT = "/mark_cart_element";
        public static final String CLEAR_SELECTED = "/clear_selected";
    }

    public static final class Messages
    {
        public static Message getCart(String nodeId)
        {
            return new Message(nodeId, Paths.GET_CART);
        }

        public static Message resultCart(String nodeId, ArrayList<CartElement> elements)
        {
            return new Message(nodeId, Paths.RESULT_CART, Serializer.serialize(elements));
        }

        public static Message markCartElement(String nodeId, CartElement cartElement)
        {
            return new Message(nodeId, Paths.MARK_CART_ELEMENT, Serializer.serialize(cartElement));
        }

        public static Message clearSelected(String nodeId)
        {
            return new Message(nodeId, Paths.CLEAR_SELECTED);
        }
    }
}