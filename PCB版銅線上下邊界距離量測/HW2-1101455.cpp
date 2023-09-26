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
	//�p�⤤���I
	int width = img.cols / 2;
	int high = img.rows / 2;
	if (img.cols % 2 != 0)
		width++;
	if (img.rows % 2 != 0)
		high++;

	cvtColor(img, gray_img, CV_BGR2GRAY);//���
	GaussianBlur(gray_img, gray_img, Size(3, 3), 0, 0);//����
	threshold(gray_img, gray_img, 125, 255, CV_THRESH_BINARY);//�G�Ȥ�

	Canny(gray_img, canny_img, 3, 9, 3);//��t�˴�
	//imshow("Disfplay Window", canny_img);
	vector<vector<Point> > contours;
	vector<Vec4i> hierarchy;
	findContours(canny_img.clone(), contours, hierarchy, RETR_EXTERNAL, CHAIN_APPROX_NONE);//������M

	//�N�y�Ф�������(�W�M�U)
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

	double m_tmp[2], b_tmp[2];//�W�U�u���ײv�Mb  y=m*x+b

	//�W�U�uø�s
	for (int i = 0; i < contours.size(); i++) {
		Vec4f line_para;
		fitLine(contours[i], line_para, CV_DIST_FAIR, 0, 1e-2, 1e-2);
		double cos_t = line_para[0];
		double sin_t = line_para[1];
		double m = sin_t / cos_t;//�ײv
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


	Point pp[2];//�ҨD�u�M�W�U�u�����I(int) �Ω�ø�s
	myPoint p[2];//�ҨD�u�M�W�U�u�����I(double) �Ω�p��Z��
	double blue_m, blue_b;//�ҨD�u���ײv�Mb


	if (m_tmp[0] == m_tmp[1] && m_tmp[0] != 0)//�W�U�u����
		blue_m = -1 / m_tmp[0]; //�ҨD�u�ײv * �W�U�䤤�@�u�ײv = -1
	else
		blue_m = tan((90 * PI / 180.0) - ((atan(m_tmp[0]) + atan(m_tmp[1])) / 2));//�|�B����


	blue_b = high - blue_m * width;// �ҨD�u b=y-m*x

	//�p��ҨD�u�M�W�U�u�����I
	p[0].x = (blue_b - b_tmp[0]) / (m_tmp[0] - blue_m);
	p[0].y = p[0].x * m_tmp[0] + b_tmp[0];
	pp[0].x = p[0].x;
	pp[0].y = p[0].y;

	p[1].x = (blue_b - b_tmp[1]) / (m_tmp[1] - blue_m);
	p[1].y = p[1].x * m_tmp[1] + b_tmp[1];
	pp[1].x = p[1].x;
	pp[1].y = p[1].y;

	line(img, pp[1], pp[0], Scalar(0, 255, 0), 1);//ø�s�ҨD�u
	cout << fixed << setprecision(4) << sqrt(pow(p[0].x - p[1].x, 2) + pow(p[0].y - p[1].y, 2)) << endl;
	//imshow("Displapy Window2", img);


	waitKey(0);
	return 0;
}
