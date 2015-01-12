package com.wenziwen.framer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

import com.wenziwen.framer.Frame.Target;

public class FrameParser {

	public static List<Frame> parse(InputStream is) throws Exception {
		
		List<Frame> books = null;  
		Frame book = null;
		Target target = null;
          
//      XmlPullParserFactory factory = XmlPullParserFactory.newInstance();  
//      XmlPullParser parser = factory.newPullParser();  
          
        XmlPullParser parser = Xml.newPullParser(); //由android.util.Xml创建一个XmlPullParser实例  
        parser.setInput(is, "UTF-8");               //设置输入流 并指明编码方式  
  
        int eventType = parser.getEventType();  
        
        while (eventType != XmlPullParser.END_DOCUMENT) {  
            switch (eventType) {  
            case XmlPullParser.START_DOCUMENT:  
                books = new ArrayList<Frame>();                  
                break;  
            case XmlPullParser.START_TAG:  
                if (parser.getName().equals("frame")) {  
                    book = new Frame();  
                    book.targets = new ArrayList<Target>();
                } else if (parser.getName().equals("file")) {  
                    eventType = parser.next();  
                    book.fileName = parser.getText();  
                } else if (parser.getName().equals("goto")) {  
                    eventType = parser.next();  
                    target = book.new Target();
                } else if (parser.getName().equals("left")) {  
                    eventType = parser.next();  
                    target.left = (Integer.parseInt(parser.getText()));  
                }   else if (parser.getName().equals("top")) {  
                    eventType = parser.next();  
                    target.top = (Integer.parseInt(parser.getText()));  
                }  else if (parser.getName().equals("width")) {  
                    eventType = parser.next();  
                    target.width = (Integer.parseInt(parser.getText()));  
                }  else if (parser.getName().equals("height")) {  
                    eventType = parser.next();  
                    target.height = (Integer.parseInt(parser.getText()));  
                }  else if (parser.getName().equals("target")) {  
                    eventType = parser.next();  
                    target.target = (Integer.parseInt(parser.getText()));  
                } 
                break;  
            case XmlPullParser.END_TAG:  
                if (parser.getName().equals("frame")) {  
                    books.add(book);  
                    book = null;      
                }   else if (parser.getName().equals("goto")) {  
                    book.targets.add(target);
                    target = null;  
                } 
                break;  
            }  
            String str = parser.getText();
            if (str !=null) 
            	Log.d("tag", parser.getText());
            eventType = parser.next();  
        }  
        return books;  
	}
}
