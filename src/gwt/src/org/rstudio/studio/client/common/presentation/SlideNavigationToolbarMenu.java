/*
 * SlideNavigationToolbarMenu.java
 *
 * Copyright (C) 2009-12 by RStudio, Inc.
 *
 * Unless you have received this program directly from RStudio pursuant
 * to the terms of a commercial license agreement with RStudio, then
 * this program is licensed to you under the terms of version 3 of the
 * GNU Affero General Public License. This program is distributed WITHOUT
 * ANY EXPRESS OR IMPLIED WARRANTY, INCLUDING THOSE OF NON-INFRINGEMENT,
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE. Please refer to the
 * AGPL (http://www.gnu.org/licenses/agpl-3.0.txt) for more details.
 *
 */

package org.rstudio.studio.client.common.presentation;

import org.rstudio.core.client.theme.res.ThemeResources;
import org.rstudio.core.client.theme.res.ThemeStyles;
import org.rstudio.core.client.widget.ScrollableToolbarPopupMenu;
import org.rstudio.core.client.widget.Toolbar;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuItem;
import com.google.gwt.user.client.ui.Widget;

public class SlideNavigationToolbarMenu 
                     implements SlideNavigationMenu
{
   public SlideNavigationToolbarMenu(Toolbar toolbar)
   {
      this(toolbar, 200, 300, false);
   }
   
   public SlideNavigationToolbarMenu(Toolbar toolbar,
                                     int maxWidth,
                                     int heightOffset,
                                     boolean separatorAfter)
   {
      titleLabel_.addStyleName(ThemeResources.INSTANCE.themeStyles()
                                          .presentationNavigatorLabel());
      titleLabel_.getElement().getStyle().setProperty("max-width", 
                                                      maxWidth + "px");
      
      menuWidget_ = toolbar.addLeftPopupMenu(titleLabel_, slidesMenu_);
      heightOffset_ = heightOffset;
      
      if (separatorAfter)
         separatorWidget_ = toolbar.addLeftSeparator();
      
      setDropDownVisible(false);
   }
   
   @Override
   public boolean isVisible()
   {
      return titleLabel_.isVisible();
   }
   
   
   @Override
   public void setVisible(boolean visible)
   {
      titleLabel_.setVisible(visible);
      setDropDownVisible(visible);
      if (separatorWidget_ != null)
         separatorWidget_.setVisible(visible);
   }

   @Override
   public void setCaption(String caption)
   {
      titleLabel_.setText(caption);
   }

   @Override
   public void addItem(MenuItem menu)
   {
      slidesMenu_.addItem(menu);  
   }

   @Override
   public void clear()
   {
      slidesMenu_.clearItems();
   }

   @Override
   public void setDropDownVisible(boolean visible)
   {
      menuWidget_.setVisible(isVisible() && visible);
   }
   
   private class SlidesPopupMenu extends ScrollableToolbarPopupMenu
   {
      public SlidesPopupMenu()
      {
         addStyleName(ThemeStyles.INSTANCE.statusBarMenu());
      }
      
      @Override
      protected int getMaxHeight()
      {
         return Window.getClientHeight() - titleLabel_.getAbsoluteTop() -
                titleLabel_.getOffsetHeight() - heightOffset_;
      }
   }
   
   private Label titleLabel_ = new Label();
   private SlidesPopupMenu slidesMenu_ = new SlidesPopupMenu();
   private Widget menuWidget_;
   private Widget separatorWidget_ = null;
   private final int heightOffset_ ;
}