package test;

import   java.util.*; 

import   java.awt.*; 
import   java.awt.event.*; 
import   javax.swing.*; 
import   javax.swing.text.*; 

public   class   Editor   extends   JFrame   { 
	private static final long serialVersionUID = 1L;
	JScrollPane   jScrollPane1   =   new   JScrollPane(); 
    JTextPane   jTextPane1   =   new   JTextPane(); 
    JToolBar   jToolBar1   =   new   JToolBar(); 
    JButton   jButton1   =   new   JButton(); 

    Hashtable<Object, Action>   actionTable   =   new   Hashtable<Object, Action>(); 
    //String[]   ActionNames   =   { "font-italic ",   "font-bold ",   "font-underline ", "font-size-24 "}; 
    private   StyledDocument   document   =   (StyledDocument)jTextPane1.getDocument(); 
    private   SimpleAttributeSet   attributes   =   new   SimpleAttributeSet(); 
    JButton   jButton2   =   new   JButton(); 
    JPopupMenu   jPopupMenu_FontFamily   =   new   JPopupMenu(); 
    JPopupMenu   jPopupMenu_FontSize   =   new   JPopupMenu(); 

    @SuppressWarnings("deprecation")
	public   static   void   main(String[]   args)   { 
        Editor   t   =   new   Editor(); 
        t.setLocation(50,   30); 
        t.setSize(500,   200); 
        t.show(); 
    } 

    public   Editor()   { 
        try   { 
            jbInit(); 
            UIManager.setLookAndFeel( "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"); 
            SwingUtilities.updateComponentTreeUI(this); 
        }   catch(Exception   e)   { 
            e.printStackTrace(); 
        } 
    } 

    private   void   jbInit()   throws   Exception   { 
        jButton1.setText( "Font   Family "); 
        jButton1.addActionListener(new   java.awt.event.ActionListener()   { 
            public   void   actionPerformed(ActionEvent   e)   { 
                jButton1_actionPerformed(e); 
            } 
        }); 
        jButton2.setText( "Font   Size "); 
        jButton2.addActionListener(new   java.awt.event.ActionListener()   { 
            public   void   actionPerformed(ActionEvent   e)   { 
                jButton2_actionPerformed(e); 
            } 
        }); 
        jTextPane1.setSelectedTextColor(new   Color(255,255,255)); 
        jTextPane1.setSelectionColor(new   Color(57,102,160)); 
        jTextPane1.setText( "jTextPane1 "); 

        this.getContentPane().add(jScrollPane1,   BorderLayout.CENTER); 
        jScrollPane1.getViewport().add(jTextPane1,   null); 
        this.getContentPane().add(jToolBar1,   BorderLayout.NORTH); 


        addWindowListener(new   WindowAdapter()   { 
            public   void   windowClosing(WindowEvent   e){ 
                System.exit(0); 
            } 
        }); 

        this.loadActionTable(); 
        this.createToolBar(); 
    } 

    void   jButton1_actionPerformed(ActionEvent   e)   { 
        this.jPopupMenu_FontFamily.show(jButton1,jButton1.getX(),jButton1.getY()+jButton1.getHeight()); 
    } 

    void   jButton2_actionPerformed(ActionEvent   e)   { 
        this.jPopupMenu_FontSize.show(jButton2,jButton1.getX(),jButton2.getY()+jButton2.getHeight()); 
    } 


//================================================================== 
    private   void   loadActionTable()   { 
        Action[]   actions   =   this.jTextPane1.getActions(); 
        for(int   i   =   0;   i   <   actions.length;   i++)   { 
            actionTable.put(actions[i].getValue(Action.NAME),   actions[i]); 
/*if(actions[i].getValue(Action.NAME).toString().indexOf( "justify ")!=   -1) 
System.out.println(   actions[i].getValue(Action.NAME));//i   +   "   : "   +*/ 
        } 
    } 

    @SuppressWarnings("unused")
	private   void   createToolBar(){ 
        String[]   Styles   =   {   "font-italic ",   "font-bold ",   "font-underline "}; 
        String[]   Sizes   =   { "font-size-8 "   , "font-size-12 ", "font-size-14 ", 
                                            "font-size-16 ", "font-size-18 ", "font-size-24 ", 
                                            "font-size-32 ", "font-size-36 ", "font-size-48 "}; 
        String[]   Familys   =   { "font-family-SansSerif ", "font-family-Serif ", "font-family-Monospaced "}; 
        String[]   Justifys   =   { "left-justify ", "center-justify ", "right-justify "}; 

        //   ToolBar 
        jToolBar1.add(jButton1,   null); 
        jToolBar1.add(jButton2,   null); 
        for(int   i   =   0;   i   <   Styles.length;   i++) 
            this.jToolBar1.add((Action)this.actionTable.get(Styles[i])); 
        //   Menu 
        for(int   i   =   0;   i   <   Familys.length;   i++) 
            this.jPopupMenu_FontFamily.add((Action)this.actionTable.get(Familys[i])); 
        System.out.println(Sizes.length); 
        for(int   i   =   0;   i   <   Sizes.length;   i++){ 
            Action   action   =   (Action)this.actionTable.get(Sizes[i]); 
            if(action   !=   null){ 
                this.jPopupMenu_FontSize.add(action); 
                System.out.println(i); 
            } 
        } 
    } 

//================================================================ 

       void   setCharacterColor(Color   c){ 
        StyleConstants.setForeground(attributes,   c); 

        int   start     =   this.jTextPane1.getSelectionStart(); 
        int   len   =   this.jTextPane1.getSelectedText().length(); 
        document.setCharacterAttributes(start,   len,   attributes,   false); 
    } 

}
