package com.example.bouncescrollbehaviour

 fun androidx.recyclerview.widget.RecyclerView.configureRecyclerGrid(elementPerRow: Int = 3): androidx.recyclerview.widget.RecyclerView {
    itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    layoutManager = androidx.recyclerview.widget.GridLayoutManager(context, elementPerRow)
    return this
}

 fun androidx.recyclerview.widget.RecyclerView.configureRecycler(): androidx.recyclerview.widget.RecyclerView {
    itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
        context,
        androidx.recyclerview.widget.RecyclerView.HORIZONTAL,
        false
    )
    return this
}
