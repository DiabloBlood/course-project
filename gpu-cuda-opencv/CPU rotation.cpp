////cxcore.lib cv.lib cvaux.lib highgui.lib cvcam.lib
#include "cv.h"
#include "highgui.h"
#include <stdio.h>
#include<time.h>
#include <math.h>
#define PI 3.14159
#include <windows.h>

clock_t start, stop;
double runTime;
int main( int argc, char** argv )
{
	char* filename = NULL;
	int width, height, step, channels;
	int width_xz,height_xz,step_xz,channels_xz;
	float degree, p, q;
	double a;
	float x, y;
	int i, j, m, n, R, G, B;
	uchar* data;
	uchar* data_xz;

	//Declare the IplImage pointer
	IplImage* pImg;										 
	IplImage* pImg_xz; 

	//Address of the Original image                             
	filename = "D:\\8192_8192.jpg";									  

	//Load the image
	pImg = cvLoadImage(filename, 1);

	//Build the image						  
	pImg_xz = cvCreateImage(cvGetSize(pImg), IPL_DEPTH_8U,3);

	start = clock();

	//Build and move the window
	cvNamedWindow( "Original Image", 1 );				          
	cvMoveWindow("Original Image",100,200);	

	/////////////////////////Image Rotation///////////////////////////////////

	width = pImg->width;
	height = pImg->height;
	step = pImg->widthStep/sizeof(uchar);
	data = (uchar*)pImg->imageData;
	channels = pImg->nChannels;

	width_xz = pImg_xz->width;
	height_xz = pImg_xz->height;
	step_xz = pImg_xz->widthStep/sizeof(uchar);
	data_xz = (uchar*)pImg_xz->imageData;
	channels_xz = pImg_xz->nChannels;

	//Take the center of the image
	width_xz=int(width_xz/2);	    	  
	height_xz=int(height_xz/2);             

	cvNamedWindow( "Rotated Image", 1 );		    
	cvMoveWindow("Rotated Image",900,200);			
	degree = 30;
	a=PI*degree/180;

	for(i=-height_xz;i<height_xz;i++)
	{
		for(j=-width_xz;j<width_xz;j++)
		{
			x=(float)(j*cos(a)-i*sin(a));
			y=(float)(j*sin(a)+i*cos(a));

			if(y>0)
			{
				m=(int)y;		 
			}

			else
			{
				m=(int)(y-1);	
			}

			if(x>0)
			{
				n=(int)x;		
			}

			else
			{
				n=(int)(x-1);	
			}

			p=x-n;
			q=y-m;

			if(q==1)
			{
				q=0;
				m=m+1;
			}

			if(p==1)
			{
				p=0;
				n=n+1;
			}

			if((m>=-height_xz)&&(m<height_xz)&&(n>=-width_xz)&&(n<width_xz))
			{
				R=(int)((1.0-q)*((1.0-p)*data[(m+height_xz)*step+channels*(n+width_xz)]+p*data[(m+height_xz)*step+channels*(n+1+width_xz)])
					+q*((1.0-p)*data[(m+height_xz+1)*step+channels*(n+width_xz)]+p*data[(m+height_xz+1)*step+channels*(n+1+width_xz)]));

				G=(int)((1.0-q)*((1.0-p)*data[(m+height_xz)*step+channels*(n+width_xz)+1]+p*data[(m+height_xz)*step+channels*(n+1+width_xz)+1])
					+q*((1.0-p)*data[(m+height_xz+1)*step+channels*(n+width_xz)+1]+p*data[(m+height_xz+1)*step+channels*(n+1+width_xz)+1]));

				B=(int)((1.0-q)*((1.0-p)*data[(m+height_xz)*step+channels*(n+width_xz)+2]+p*data[(m+height_xz)*step+channels*(n+1+width_xz)+2])
					+q*((1.0-p)*data[(m+height_xz+1)*step+channels*(n+width_xz)+2]+p*data[(m+height_xz+1)*step+channels*(n+1+width_xz)+2]));
			}

			else
			{
				R=0;
				G=0;
				B=0;
			}

			if(R<0||G<0||B<0)
			{
				R=0;
				G=0;
				B=0;
			}

			if(R>255||G>255||B>255)
			{
				R=255;
				G=255;
				B=255;
			}

			data_xz[(i+height_xz)*step_xz+channels_xz*(j+width_xz)]=R;
			data_xz[(i+height_xz)*step_xz+channels_xz*(j+width_xz)+1]=G;
			data_xz[(i+height_xz)*step_xz+channels_xz*(j+width_xz)+2]=B;
		}
	}

	stop = clock();
	runTime = (((double) (stop - start)) / CLK_TCK)*1000;
	printf("CPU run time = %6f ms\n", runTime);
	cvShowImage( "Rotated Image", pImg_xz );				 
	cvShowImage( "Original Image", pImg );				     
	Sleep(100);

	cvWaitKey(0);									
	cvDestroyWindow( "Original Image" );						
	cvReleaseImage( &pImg );						
	cvDestroyWindow( "Rotated Image" );						
	cvReleaseImage( &pImg_xz );					
	return 0;
}


