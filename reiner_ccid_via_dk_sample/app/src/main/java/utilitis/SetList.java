/*
* 
*   
* 
*   Created by Steve Spormann on 5.5.2015.
*   Copyright (c) 2015 REINER SCT. All rights reserved.
* 
*   Version:    0.5.3
*   Date:       1.03.2017
*   Autor:      S. Spormann
*   eMail:      mobile-sdk@reiner-sct.com
*/
package utilitis;

import java.util.ArrayList;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Class SetList.
 *
 * @param <T> the generic type
 */
public class SetList<T> extends ArrayList<T> {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /* (non-Javadoc)
     * @see java.util.ArrayList#add(java.lang.Object)
     */
    @Override
    public synchronized boolean add(T t) {
        return !super.contains(t) && super.add(t);
    }

    /* (non-Javadoc)
     * @see java.util.ArrayList#add(int, java.lang.Object)
     */
    @Override
    public synchronized void add(int index, T element) {
        if (!super.contains(element)) super.add(index, element);
    }

    /* (non-Javadoc)
     * @see java.util.ArrayList#addAll(java.util.Collection)
     */
    @Override
    public synchronized boolean addAll(Collection<? extends T> c) {
        boolean added = false;
        for (T t : c)
            added |= add(t);
        return added;
    }

    /* (non-Javadoc)
     * @see java.util.ArrayList#addAll(int, java.util.Collection)
     */
    @Override
    public synchronized boolean addAll(int index, Collection<? extends T> c) {
        boolean added = false;
        for (T t : c)
            if (!super.contains(t)) {
                super.add(index++, t);
                added = true;
            }
        return added;
    }
}
