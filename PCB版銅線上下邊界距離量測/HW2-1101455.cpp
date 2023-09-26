#include<opencv2/opencv.hpp>
#include <opencv2/imgproc/imgproc_c.h>
#include<iostream>
#include<cmath>
#include<string>
#include <iomanip>
#define PI 3.14159265
using namespace std;
using namespace cv;
struct myPoint {
	double x, y;
};


int main(int argc, char* argv[])
{
	//String img_path = argv[1];
	String img_path = "public6.jpg";
	Mat img = imread(img_path, 1), canny_img, gray_img;
	if (img.empty()) {
		cout << "Couldn't read the img: " << img_path << endl;
		return 1;
	}
	//計算中心點
	int width = img.cols / 2;
	int high = img.rows / 2;
	if (img.cols % 2 != 0)
		width++;
	if (img.rows % 2 != 0)
		high++;

	cvtColor(img, gray_img, CV_BGR2GRAY);//轉灰
	GaussianBlur(gray_img, gray_img, Size(3, 3), 0, 0);//高斯
	threshold(gray_img, gray_img, 125, 255, CV_THRESH_BINARY);//二值化

	Canny(gray_img, canny_img, 3, 9, 3);//邊緣檢測
	//imshow("Disfplay Window", canny_img);
	vector<vector<Point> > contours;
	vector<Vec4i> hierarchy;
	findContours(canny_img.clone(), contours, hierarchy, RETR_EXTERNAL, CHAIN_APPROX_NONE);//輪廓找尋

	//將座標分為兩邊(上和下)
	vector<Point>line1;
	vector<Point>line2;
	int maxY = 0, minY = img.rows + 1;
	for (auto i : contours) {		
		for (auto j : i) {
			if (maxY < j.y)
				maxY = j.y;
			if (minY > j.y)
				minY = j.y;
		}

	}
	int tmpy = (maxY - minY) / 2 + minY;
	
	
	for (auto i : contours) {
		
		for (auto j : i) {

			if (j.y > tmpy)
				line1.push_back(j);
			else
				line2.push_back(j);
			
		}

	}
	contours.clear();
	contours.push_back(line1);
	contours.push_back(line2);

	double m_tmp[2], b_tmp[2];//上下線的斜率和b  y=m*x+b

	//上下線繪製
	for (int i = 0; i < contours.size(); i++) {
		Vec4f line_para;
		fitLine(contours[i], line_para, CV_DIST_FAIR, 0, 1e-2, 1e-2);
		double cos_t = line_para[0];
		double sin_t = line_para[1];
		double m = sin_t / cos_t;//斜率
		m_tmp[i] = m;


		double b = line_para[3] - m * line_para[2];// b=y-m*x
		b_tmp[i] = b;

		Point point1, point2;
		point1.x = img.rows;
		point1.y = m * point1.x + b;

		point2.x = 0;
		point2.y = m * point2.x + b;

		line(img, point1, point2, Scalar(0, 255, 0), 1);

	}
	//imshow("Display Window", img);


	Point pp[2];//所求線和上下線的交點(int) 用於繪製
	myPoint p[2];//所求線和上下線的交點(double) 用於計算距離
	double blue_m, blue_b;//所求線的斜率和b


	if (m_tmp[0] == m_tmp[1] && m_tmp[0] != 0)//上下線平行
		blue_m = -1 / m_tmp[0]; //所求線斜率 * 上下其中一線斜率 = -1
	else
		blue_m = tan((90 * PI / 180.0) - ((atan(m_tmp[0]) + atan(m_tmp[1])) / 2));//四、公式


	blue_b = high - blue_m * width;// 所求線 b=y-m*x

	//計算所求線和上下線的交點
	p[0].x = (blue_b - b_tmp[0]) / (m_tmp[0] - blue_m);
	p[0].y = p[0].x * m_tmp[0] + b_tmp[0];
	pp[0].x = p[0].x;
	pp[0].y = p[0].y;

	p[1].x = (blue_b - b_tmp[1]) / (m_tmp[1] - blue_m);
	p[1].y = p[1].x * m_tmp[1] + b_tmp[1];
	pp[1].x = p[1].x;
	pp[1].y = p[1].y;

	line(img, pp[1], pp[0], Scalar(0, 255, 0), 1);//繪製所求線
	cout << fixed << setprecision(4) << sqrt(pow(p[0].x - p[1].x, 2) + pow(p[0].y - p[1].y, 2)) << endl;
	//imshow("Displapy Window2", img);


	waitKey(0);
	return 0;
}
