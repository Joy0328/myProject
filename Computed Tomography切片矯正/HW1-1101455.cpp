#include<opencv2/opencv.hpp>
#include <opencv2/imgproc/imgproc_c.h>
#include<iostream>
#include<string>
using namespace std;
using namespace cv;

int main(int argc, char* argv[])
{
	Mat img = imread("public_08.png", 1);
	Mat tmp_img, img_out;
	if (img.empty()) {
		cout << "Couldn't read the img: " << img << endl;
		return 1;
	}

	Rect ccomp;
	cvtColor(img, img, CV_BGR2GRAY);//Âà¦Ç

	floodFill(img, Point(0, 0), Scalar(0, 0, 0), &ccomp, Scalar(210, 210, 210), Scalar(210, 210, 210), FLOODFILL_FIXED_RANGE);//¬x¤ô¶ñ¥Rºtºâªk 
	tmp_img = img.clone();


	vector<vector<Point> > contours;
	vector<Vec4i> hierarchy;
	findContours(tmp_img, contours, hierarchy, RETR_LIST, CHAIN_APPROX_NONE);//§ä½ü¹ø

	unsigned long long mx = 0;
	double area_mx = contourArea(InputArray(contours[0]), false);
	for (unsigned long long i = 1; i < contours.size(); i++) {
		double tmp = contourArea(InputArray(contours[i]), false);
		if (area_mx < tmp) {
			area_mx = tmp;
			mx = i;
		}
	}//§ä¥X½ü¹ø­±¿n³Ì¤jªÌ
	img_out = tmp_img.clone();
	drawContours(tmp_img, contours, mx, Scalar(0), CV_FILLED, 8, hierarchy);
	//½ü¹øÃ¸»s*/
	bitwise_xor(img_out, tmp_img, img_out);
	imshow("Display Window", tmp_img);


	RotatedRect rect = fitEllipse(contours[mx]);
	ellipse(tmp_img, rect, Scalar(0, 0, 0), 3);//¾ò¶ê¶É¨¤
	//cout << rect.angle << endl;

	double angle;
	if (rect.angle > 90)
		angle = rect.angle - 180;
	else
		angle = rect.angle;
	int R = img_out.rows;
	int C = img_out.cols;
	Mat M = getRotationMatrix2D(Point2f(R / 2, C / 2), angle, 1.0);
	warpAffine(img_out, img_out, M, img_out.size());//rotation


	cvtColor(img_out, img_out, COLOR_GRAY2RGB);

	imshow("Display Window", img_out);



	waitKey(0);
	return 0;
}
