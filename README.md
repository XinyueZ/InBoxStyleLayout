# InboxLayout
Like Google Inbox effect to open-close list and detail by dragging up/down.

This version supports RecyclerView as "background" which shows list of items.

The InBoxLayoutXXXX can still be used for showing detail.

# ScreenShot
![image](https://raw.githubusercontent.com/zhaozhentao/InboxLayout/master/screenshot/pic.gif)

![image](https://github.com/zhaozhentao/InboxLayout/blob/master/screenshot/pic1.gif)

# Usage
###step 1 

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        
        <com.zzt.inbox.widget.InboxScrollView
            android:scrollbars="none"
            android:id="@+id/scroll"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <LinearLayout
                android:orientation="vertical"
                android:layout_width="match_parent"
                android:layout_height="wrap_content">
                
            </LinearLayout>
        </com.zzt.inbox.widget.InboxScrollView>
        
        <com.zzt.inbox.widget.InboxLayoutListView
            android:id="@+id/inboxlayout"
            android:visibility="invisible"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        </com.zzt.inbox.widget.InboxLayoutListView>
    </FrameLayout>

###step 2

    final InboxScrollView inboxScrollView = (InboxScrollView)findViewById(R.id.scroll);
    inboxLayout = (InboxLayout)findViewById(R.id.inboxlayout);             
    inboxLayout.seBackgroundScrollView(inboxScrollView);  
    
    
###step 3
 
    final LinearLayout dingdan = (LinearLayout)findViewById(R.id.ding_dan);
    dingdan.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        inboxLayout.openWithAnim(dingdan); 
      }
    });

##### Thanks

For good start thanks [zhaozhentao](https://raw.githubusercontent.com/zhaozhentao/)

