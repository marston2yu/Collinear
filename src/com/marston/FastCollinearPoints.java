package com.marston;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class FastCollinearPoints {

    private LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {

        // 复制数组进行操作，不改变原数组
        Point[] pointsForCope = Arrays.copyOf(points, points.length);

        // Bag 用于存放线段
        ArrayList<LineSegment> segmentsSet = new ArrayList<>();

        for (int i = 0; i < points.length; i++) {
            // 先按左下到上排序点
            Arrays.sort(pointsForCope);
            // 根据相对于原数组的第i个点的斜率排序
            Arrays.sort(pointsForCope, points[i].slopeOrder());
            // 起始点
            Point start = points[i];
            for (int j = 0; j < pointsForCope.length - 1; j++) {
                // 跳过在原数组第i个点左侧的点，最早找到点为左端点
                if (start.compareTo(pointsForCope[j]) >= 0) continue;

                // 获得j到i斜率
                double slope = start.slopeTo(pointsForCope[j]);
                // 如果下一个点斜率与之相等，则继续查看下一个点，直至不同
                Point end = null;   // 末端
                Boolean find = false;  // 找到共线点标志
                if (slope == start.slopeTo(pointsForCope[j+1])) {
                    int startIndex = j;
                    // 顺次找到共线的点，直到发现不共线点或者到达数组末尾，数组末尾需要另行判断
                    while (j < pointsForCope.length - 1 && start.slopeTo(pointsForCope[++j]) == slope) {}
                    // 共线点为0、startIndex～j-1或0、startIndex～j（当j为终点时）
                    if (start.slopeTo(pointsForCope[j]) == slope) j++;
                    if (j - 1 - startIndex >= 2) {
                        end = pointsForCope[j - 1];
                        find = true;
                    }
                }
                if (find) {
                    segmentsSet.add(new LineSegment(start, end));
                    find = false;
                }

            }

        }

        // 将容器中数据放到数组中
        segments = new LineSegment[segmentsSet.size()];
        Iterator<LineSegment> iter = segmentsSet.iterator();
        for (int i = 0; i < segments.length; i++) {
            segments[i] = iter.next();
            System.out.println(segments[i]);
        }
    }

    public int numberOfSegment() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return  segments;
    }
}
