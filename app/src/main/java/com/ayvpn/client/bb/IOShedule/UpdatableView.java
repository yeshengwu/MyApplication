package com.ayvpn.client.bb.IOShedule;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by shidu on 15/11/11.
 */
public interface UpdatableView  {

    /**
     * Updates the view based on data in the model.
     *
     * @param model The updated model.
     * @param query The query that has triggered the model update. This is so not the full view has
     *              to be updated but only specific elements of the view, depending on the query.
     */
    public void displayData();

    /**
     * Displays error message resulting from a query not succeeding.
     *
     * @param query The query that resulted in an error.
     */
    public void displayErrorMessage();

    /**
     * Data URI representing the data displayed in this view. Complex views may use data from
     * different queries / Data URI.
     *
     * @param query The query for which the URI should be returned.
     */
    public Uri getDataUri();

    /**
     * A listener for events fired off by a {@link Model}
     */
    interface UserActionListener {

        /**
         * Called when the user has performed an {@code action}, with data to be passed
         * as a {@link android.os.Bundle} in {@code args}.
         * <p />
         * Add the constants used to store values in the bundle to the Model implementation class
         * as final static protected strings.
         */
        public void onUserAction(String action, @Nullable Bundle args);
    }
}
