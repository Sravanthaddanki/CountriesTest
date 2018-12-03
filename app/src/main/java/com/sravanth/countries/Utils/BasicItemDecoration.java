

package com.sravanth.countries.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ItemDecoration;
import androidx.recyclerview.widget.RecyclerView.LayoutParams;
import androidx.recyclerview.widget.RecyclerView.State;
import android.view.View;

import com.sravanth.countries.R;
import com.sravanth.countries.wrappers.AndroidWrapper;


public class BasicItemDecoration extends ItemDecoration
{

    public BasicItemDecoration(@NonNull Context context, @NonNull AndroidWrapper androidWrapper, boolean dividerAfterLastItem )
    {
        this.dividerAfterLastItem = dividerAfterLastItem;

        drawable = androidWrapper.getDrawable( context, R.drawable.basic_item_decoration );
    }
    
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state )
    {
        super.getItemOffsets( outRect, view, parent, state );
        
        if( parent.getChildAdapterPosition( view ) == 0 )
        {
            return;
        }
        
        outRect.top = 10;
    }
    
    @Override
    public void onDraw(@NonNull Canvas canvas, @NonNull RecyclerView parent, @Nullable State state )
    {
        int dividerLeft = parent.getPaddingLeft();
        int dividerRight = parent.getWidth() - parent.getPaddingRight();
        
        int childCount = parent.getChildCount();
        
        childCount = dividerAfterLastItem ? childCount : childCount - 1;
        
        for( int i = 0; i < childCount; i++ )
        {
            View child = parent.getChildAt( i );
            
            LayoutParams params = (LayoutParams) child.getLayoutParams();
            
            int dividerTop = child.getBottom() + params.bottomMargin;
            int dividerBottom = dividerTop + drawable.getIntrinsicHeight();
            
            drawable.setBounds( dividerLeft, dividerTop, dividerRight, dividerBottom );
            drawable.draw( canvas );
        }
    }
    
    private final Drawable drawable;
    private final boolean dividerAfterLastItem;
}