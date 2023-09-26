#include<opencv2/opencv.hpp>
#include <opencv2/imgproc/imgproc_c.h>
#include<iostream>
#include<algorithm>
#include<string>
#include<vector>
using namespace std;
using namespace cv;
struct POINT
{
	int lx, ly, rx, ry;
};

bool cmp(POINT& a, POINT& b) {
	if (a.lx < b.lx) {
		return true;
	}
	else if (a.lx > b.lx) {
		return false;
	}
	else {
		if (a.ly < b.ly) {
			return true;
		}
		else {
			return false;

		}
	}
}
void F(Mat& img) {
	medianBlur(img, img, 3);

	Mat element = getStructuringElement(MORPH_ELLIPSE, Size(3, 3), Point(1, 1));
	erode(img, img, element);//侵蝕

	Mat element1 = getStructuringElement(MORPH_ELLIPSE, Size(5, 5), Point(3, 3));
	dilate(img, img, element1);//膨脹

}

int main(int argc, char* argv[])
{
	String img_path = argv[1];
	//String img_path = "public11.jpg";
	Mat img = imread(img_path, 1);
	Mat hsv, mask, mask_B, mask_R, mask_Y, mask_tmp;
	if (img.empty()) {
		cout << "Couldn't read the img: " << img_path << endl;
		return 1;
	}
	cvtColor(img, hsv, CV_BGR2HSV);//toHSV


	inRange(hsv, Scalar(0, 65, 50), Scalar(10, 255, 255), mask_tmp);
	inRange(hsv, Scalar(160, 65, 50), Scalar(190, 255, 255), mask_R);
	mask_R = mask_tmp | mask_R;//red
	inRange(hsv, Scalar(20, 170, 170), Scalar(25, 255, 255), mask_Y);//yellow
	inRange(hsv, Scalar(100, 100, 50), Scalar(130, 255, 255), mask_B);//blue 


	//圖片雜點清除
	F(mask_R);
	F(mask_Y);
	F(mask_B);
	mask = mask_R | mask_B | mask_Y;//合併
	imshow("mask_R", mask_R);
	imshow("mask_Y", mask_Y);
	imshow("mask_B", mask_B);/**/


	vector<vector<Point> > contours;
	findContours(mask.clone(), contours, CV_RETR_EXTERNAL, CV_CHAIN_APPROX_SIMPLE);//找輪廓

	vector<POINT>mypoint;
	vector<Rect> rects;
	Mat img_tmp = Mat::zeros(mask.size(), CV_8UC3);
	for (int i = 0; i < contours.size(); i++)
	{
		rects.push_back(boundingRect(Mat(contours[i])));
		float R = (float)rects[i].width / rects[i].height;
		float A = (float)contourArea(contours[i]);
		if (A < 200)
			continue;
		if (R > 1.6 || R < 0.4)
			continue;



		drawContours(img_tmp, contours, i, Scalar(255, 255, 255), CV_FILLED, 8);//畫輪廓
		rectangle(img_tmp, rects[i], Scalar(255, 255, 255), 1, 16);


		Mat gray, dst, nor;
		cvtColor(img_tmp, gray, CV_BGR2GRAY);//toGRAY
		cornerHarris(gray, dst, 2, 3, 0.04);//角點偵測
		normalize(dst, nor, 0, 255, NORM_MINMAX, CV_32FC1, Mat());//歸一化
		convertScaleAbs(nor, nor);//加強
		int n = 0;
		for (int j = 0; j < nor.rows; j++)
		{
			uchar* curRow = nor.ptr(j);
			for (int i = 0; i < nor.cols; i++)
			{
				if ((int)*curRow > 160) {
					n++;
				}
				curRow++;
			}
		}
		if (n > 50)
			continue;
		rectangle(img, rects[i], Scalar(0, 255, 0), 1, 16);//畫矩形
		mypoint.push_back({ rects[i].x ,rects[i].y ,rects[i].x + rects[i].width ,rects[i].y + rects[i].height });//存座標
	}
	sort(mypoint.begin(), mypoint.end(), cmp);//座標排序
	for (int i = 0; i < mypoint.size(); i++)
		cout << mypoint[i].lx << " " << mypoint[i].ly << " " << mypoint[i].rx << " " << mypoint[i].ry << endl;//輸出座標

	imshow("ans", img);
	waitKey();
	return 0;
}