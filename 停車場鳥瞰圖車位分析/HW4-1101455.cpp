#include<opencv2/opencv.hpp>
#include <opencv2/imgproc/imgproc_c.h>
#include<iostream>
#include<string>
#include<math.h>
using namespace std;
using namespace cv;
vector<Vec4i> linesP;
int spaceHight;
int y;
int flag;

void F(Mat& img) {
	medianBlur(img, img, 3);

	Mat element = getStructuringElement(MORPH_ELLIPSE, Size(3, 3), Point(1, 1));
	erode(img, img, element);//侵蝕

	Mat element1 = getStructuringElement(MORPH_ELLIPSE, Size(5, 5), Point(3, 3));

	dilate(img, img, element1);//膨脹


}

void G(Mat img) {
	Mat spaceCanny, hsv, tmp;
	int imgHight = img.rows;
	cvtColor(img, hsv, CV_BGR2HSV);//toHSV
	inRange(hsv, Scalar(0, 0, 170), Scalar(180, 24, 224), hsv);
	F(hsv);
	Canny(hsv, spaceCanny, 50, 150);//邊緣檢測

	HoughLinesP(spaceCanny, linesP, 1, CV_PI / 180, 100, 100, 10);
	y = 9999;
	flag = 0;
	spaceHight = 0;
	for (size_t i = 0; i < linesP.size(); i++)
	{
		Vec4i l = linesP[i];
		if (spaceHight < abs(l[1] - l[3]) && abs(l[1] - l[3]) <= imgHight / 2)
			spaceHight = abs(l[1] - l[3]);
		if (y > l[1])
			y = l[1];
		if (y > l[3])
			y = l[3];
		if (abs(l[0] - l[2]) > 1)
			flag++;
		if (flag > 8)
			break;
	}


}

int main(int argc, char* argv[])
{
	String img_path = argv[1];
	//String img_path = "public5.jpg";
	Mat img = imread(img_path, 1);
	Mat carCanny, gray, carTmp, spaceTmp;
	carTmp = img.clone();
	spaceTmp = img.clone();
	G(spaceTmp);
	if (flag > 8) {
		linesP.clear();
		transpose(img, img);
		spaceTmp = img.clone();
		G(spaceTmp);
	}

	cvtColor(carTmp, carCanny, CV_BGR2GRAY);//轉灰
	Canny(carCanny, carCanny, 50, 150);
	vector<vector<Point> >contours;
	findContours(carCanny, contours, CV_RETR_EXTERNAL, CHAIN_APPROX_NONE);
	int carNum = 0;
	for (int i = 0; i < contours.size(); i++) {

		if (contours[i].size() > 1200) {
			carNum++;
		}
	}

	int imgWidth = img.cols;
	int spaceWidth = spaceHight / 5 * 2.4;
	int spaceNum = imgWidth / spaceWidth;



	cout << carNum << " " << spaceNum * 2 - carNum << endl;

	waitKey(0);
	return 0;
}
