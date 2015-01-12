package com.wenziwen.framer;

import java.util.List;

public class Frame {
	@Override
	public String toString() {
		return "Frame [fileName=" + fileName + ", targets=" + targets + "]";
	}

	public String fileName;
	public List<Target> targets;
	
	public class Target {
		public Target(){
			
		}
		@Override
		public String toString() {
			return "Target [left=" + left + ", top=" + top + ", width=" + width
					+ ", height=" + height + ", target=" + target + "]";
		}
		public int left;
		public int top;
		public int width;
		public int height;
		public int target;
	}
}
