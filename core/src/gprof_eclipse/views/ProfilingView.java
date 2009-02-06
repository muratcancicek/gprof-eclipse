/*
 * =======================================
 * ============ gprof-eclipse ============
 * =======================================
 * 
 * File: ProfilingView.java
 * 
 * ---------------------------------------
 * 
 * Last changed:
 * $Revision$
 * $Author$
 * $Date$
 */

package gprof_eclipse.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import profiled.*;

/**
 * Displays the profiler results.
 */

public class ProfilingView extends ViewPart
{
	/** Holds the profiler's report. */
	private ProfilerReport report;
	
	/** Holds the actual graphical view. */
	private TableViewer viewer;
	
	/** Holds the "Run With Profiling" action. */
	private Action runWithProfiling;
	
	/**
	 * Provides the profiler report to the view in a displayable format.
	 * 
	 * @author chrisculy
	 */
	class ViewContentProvider implements IStructuredContentProvider
	{
		public void inputChanged(Viewer v, Object oldInput, Object newInput)
		{
			/* stub function */
		}
		
		public void dispose()
		{
			/* stub function */
		}
		
		public Object[] getElements(Object parent)
		{
			return new String[] { "One", "Two", "Three" };
		}
	}
	
	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider
	{
		public String getColumnText(Object obj, int index)
		{
			return this.getText(obj);
		}
		
		public Image getColumnImage(Object obj, int index)
		{
			return this.getImage(obj);
		}
		
		public Image getImage(Object obj)
		{
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}
	
	/**
	 * Constructor.
	 */
	public ProfilingView()
	{
		/* stub function */
	}
	
	/**
	 * Create the viewer.
	 */
	public void createPartControl(Composite parent)
	{
		this.viewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		this.viewer.setContentProvider(new ViewContentProvider());
		this.viewer.setLabelProvider(new ViewLabelProvider());
		this.viewer.setInput(this.getViewSite());
		
		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.viewer.getControl(), "gprof_eclipse.viewer");
		this.makeActions();
		this.hookContextMenu();
		this.contributeToActionBars();
	}
	
	private void hookContextMenu()
	{
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener()
		{
			public void menuAboutToShow(IMenuManager manager)
			{
				ProfilingView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(this.viewer.getControl());
		this.viewer.getControl().setMenu(menu);
		this.getSite().registerContextMenu(menuMgr, this.viewer);
	}
	
	private void contributeToActionBars()
	{
		IActionBars bars = this.getViewSite().getActionBars();
		this.fillLocalPullDown(bars.getMenuManager());
		this.fillLocalToolBar(bars.getToolBarManager());
	}
	
	private void fillLocalPullDown(IMenuManager manager)
	{
		manager.add(this.runWithProfiling);
	}
	
	private void fillContextMenu(IMenuManager manager)
	{
		manager.add(this.runWithProfiling);
		// Other plug-ins can contribute there actions here.
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}
	
	private void fillLocalToolBar(IToolBarManager manager)
	{
		manager.add(this.runWithProfiling);
	}
	
	private void makeActions()
	{
		this.runWithProfiling = new Action()
		{
			public void run()
			{
				ProfilingView.this.showMessage("Running profiler...");
			}
		};
		this.runWithProfiling.setText("Run With Profiling");
		this.runWithProfiling.setToolTipText("Runs the current project with profiling and displays a report when finished.");
		this.runWithProfiling.setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
			ISharedImages.IMG_OBJS_INFO_TSK));
	}
	
	private void showMessage(String message)
	{
		MessageDialog.openInformation(this.viewer.getControl().getShell(), "Profiler Results", message);
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus()
	{
		this.viewer.getControl().setFocus();
	}
}
