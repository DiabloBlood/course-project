# Image-Rotation-Performance-Analysis-of-GPU-and-CPU
/*GPU CUDA code was used by CUDA offical samples*/
##1. Introduction
This project is aimed at implementing a GPU solution of image rotation by inverse transformation. The basic geometric transformation of image concludes the operations of translation, scaling, shearing and rotation. It is easy to implement translation and scaling by just use addition processing of pixels. So it is difficult to enhance the efficiency of transformation algorithm by GPU. However, since there are many multiplications of rotation transformation, it could largely reduce the execution time to implement this operation by GPU.

##2. Algorithm
For rotation transformation, the original point is (x, y) and the new point will be (x′, y′) after rotated along the center point (x0, y0). However, since the positions of pixels of an image are discrete, it is possible that the new point (x′, y′) was not precise to have a correct integer coordinate of pixels, which called a hole [1]. So it is necessary to use inverse transformation [2].
* * *
#### x' = (x - x0)cos(a) - (y - y0)sin(a)                     -----Equation (1)
#### y' = (y - y0)cos(a) - (x - x0)sin(a)
* * *
Inversion transformation
#### x = x'cos(a) - y'sin(a) + x0                             -----Equation (2)
#### y = y'cos(a) - x'sin(a) + y0
* * *
Assume that p, q are two original points, p′ and q′ are new points after rotated
#### Xq' = Xp' + ΔX                                           -----Equation (3)
#### Xq' = Xp' + ΔY
* * *
Take (3) into (2), we could obtain
#### Xq = Xp + ΔX * cos(a) + ΔY * sin(a)                      -----Equation (4)
#### Yq = Yp + ΔY * cos(a) + ΔX * sin(a)
* * *
If p and q in a row, then we only need to know the coordinate of new original point, and we could he coordinate of new original point to calculate all of the other point coordinates by add Δx and Δy

#### ΔX = 0 * cos(a) ---------- Xq' = X0                            -----Equation (5)
#### ΔX = 1 * cos(a) ---------- Xq' = X1
#### ΔX = (n - 1) * cos(a) ---- Xq' = Xn
* * *
#### ΔY = 0 * sin(a) ----------- Xq' = X0                            -----Equation (6)
#### ΔY = -1 * sin(a) ---------- Xq' = X1
#### ΔY = -(n - 1) * sin(a) ---- Xq' = Xn

This algorithm has a typical feature of parallel computing. It is easy be implemented of SIMT.


