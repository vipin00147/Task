package com.example.test_task.utils;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EndlessRecyclerViewScroll extends RecyclerView.OnScrollListener {

    private View bottomBar = null;

    private boolean loading = true;             // True if we are still waiting for the last set of data to load
    private int current_page = 1;               // The current offset index of data you have loaded
    public int previousTotal = 0;              // The total number of items in the RecyclerView after the last load
    int totalItemCount;                 // The total number of items in the RecyclerView
    private int visibleItemCount;               // The total number of visible items in the RecyclerView
    private int firstVisibleItem;               // The current position of first visible item in the total visible items
    private int visibleThreshold;               // The minimum amount of items to have below your current scroll position before loading more


    public EndlessRecyclerViewScroll() {
    }

    public EndlessRecyclerViewScroll(View bottomBar) {
        this.bottomBar = bottomBar;
    }


    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

//        int visibleItemCount = ((LinearLayoutManager)recyclerView.getLayoutManager()).getChildCount();
//        int totalItemCount = ((LinearLayoutManager)recyclerView.getLayoutManager()).getItemCount();
//        int firstVisibleItemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
//        if (!loading && !isLastPage()) {
//            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
//                    && firstVisibleItemPosition >= 0
//                    && totalItemCount >= PAGE_SIZE) {
//                loadMoreItems();
//            }
//        }

        if (dy > 0) {

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = recyclerView.getLayoutManager().getItemCount();
            visibleThreshold = 1;
//            visibleThreshold = ((GridLayoutManager)recyclerView.getLayoutManager()).getSpanCount();
            firstVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
            int lastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

            // If it's still loading, and the RecyclerView count has changed
            if (loading && (totalItemCount > previousTotal)) {
                previousTotal = totalItemCount;
                loading = false;
            }


            if (!loading &&  lastVisibleItem == totalItemCount-1) {
                // End has been reached

                loading = true;
                current_page++;

                recyclerView.scrollToPosition(lastVisibleItem);

                onLoadMore(current_page);
            }


/*            if (bottomBar != null) {
                // Animate the loading view to 0% opacity. After the animation ends,
                // set its visibility to GONE as an optimization step (it won't participate in layout passes, etc.)
                bottomBar.animate()
                        .alpha(0f)
                        .setDuration(200)
                        .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                bottomBar.setVisibility(View.GONE);
                            }
                        });
            }*/


        }

        /*else if (dy < 0) {

            int lastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition();

            if (!loading && (totalItemCount > previousTotal)) {
                previousTotal = totalItemCount;
                loading = false;
            }


            if (!loading &&  lastVisibleItem <= totalItemCount-1) {
                // Top has been reached

                loading = true;
                current_page--;
                recyclerView.scrollToPosition(lastVisibleItem);
                onLoadMore(current_page);
            }



            if (bottomBar != null) {
                // Set the content view to 0% opacity but visible, so that it is visible
                // (but fully transparent) during the animation.
                bottomBar.setAlpha(0f);
                bottomBar.setVisibility(View.VISIBLE);

                // Animate the content view to 100% opacity, and clear any animation listener set on the view.
                bottomBar.animate()
                        .alpha(1f)
                        .setDuration(200)
                        .setListener(null);
            }
        }*/

    }


    public  void onLoadMore(int current_page) {

    }

    public void resetPreviousTotal() {
        previousTotal = 0;
        current_page = 1;
    }
}
