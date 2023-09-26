


#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <semaphore.h>
#include <time.h>

int fishNumber=1;//計算抓到第幾隻魚了
int totalFish;//共有幾隻魚
int tableSpace;//備料台數

int totalCuttingFactory;
int totalCanningFactory;

volatile int milliseconds = 0;//計時用

pthread_mutex_t cutQueueMutex;
pthread_mutex_t canQueueMutex;

sem_t cutSemaphore;//切割工廠數
sem_t canSemaphore;//罐頭工廠數


void* timer(void* arg) {//計時器
    while (1) {
    	usleep(1000);
        milliseconds++;

    }
    return NULL;
}

typedef struct {
    int fish_num;//魚的編號
    int cutted;//切割標記

} Fish;

typedef struct {
    Fish* queue_data;
    int front;
    int rear;
    int count;
    int capacity;
} Queue;


//cuttingQueue+canningQueue=備料台數
Queue cuttingQueue;
Queue canningQueue;

void iniQueue(Queue* queue, int tableSpace) {
    queue->queue_data = (Fish*)malloc(sizeof(Fish) * tableSpace);
    queue->front = 0;
    queue->rear = 0;
    queue->count = 0;
    queue->capacity = tableSpace;
}

void enqueue(Queue* queue, Fish fish) {
    if (queue->count >= queue->capacity) {
        return;
    }

    queue->queue_data[queue->rear] = fish;
    queue->rear = (queue->rear + 1) % queue->capacity;
    queue->count++;
}

Fish dequeue(Queue* queue) {
    if (queue->count <= 0) {
        Fish emptyFish;
        emptyFish.fish_num = -1;
        return emptyFish;
    }

    Fish fish = queue->queue_data[queue->front];
    queue->front = (queue->front + 1) % queue->capacity;
    queue->count--;

    return fish;
}

void* freezer(void* arg) {

    Fish* fish = (Fish*)arg;


    while(1){

        int freezingTime = rand() % 31 + 20;

        printf("%dms--Fish#%d: has been sent to the Freezer - %dms\n",milliseconds, fish->fish_num,freezingTime);

        int tmp=milliseconds;

        while(milliseconds-tmp<freezingTime);//冷凍中

        pthread_mutex_lock(&cutQueueMutex);
        pthread_mutex_lock(&canQueueMutex);

            if (cuttingQueue.count + canningQueue.count < tableSpace) {//如果備料台沒滿，送到備料台
                if(fish->cutted)
                    enqueue(&canningQueue, *fish);//如果切過了，送到canningQueue
                else
                    enqueue(&cuttingQueue, *fish);//如果沒切過，送到cuttingQueue

                printf("%dms--Fish#%d: waiting in the slot\n",milliseconds, fish->fish_num);

                pthread_mutex_unlock(&canQueueMutex);
                pthread_mutex_unlock(&cutQueueMutex);

                break;
            }


        pthread_mutex_unlock(&canQueueMutex);
        pthread_mutex_unlock(&cutQueueMutex);
        
        //如果備料台滿了，繼續冰


    }
    free(arg);

}

void* cuttingFactory(void* arg) {

    sem_wait(&cutSemaphore);//用掉一間切割工廠

    int cuttingTime =rand() % 11 + 20;

    Fish* fish = malloc(sizeof(Fish));

    pthread_mutex_lock(&cutQueueMutex);//防止有人剛好在對cuttingQueue操作


        while (cuttingQueue.count <= 0) {//等待魚進來

            pthread_mutex_unlock(&cutQueueMutex);
                usleep(1000);//避免deadlock
            pthread_mutex_lock(&cutQueueMutex);

        }

        Fish fishTmp = dequeue(&cuttingQueue);
        totalCuttingFactory--;

    pthread_mutex_unlock(&cutQueueMutex);

    *fish = fishTmp;


    printf("%dms--Fish#%d: enters the CUTTER\n", milliseconds, fish->fish_num);

    printf("%dms--CUTTER: cutting... cutting... Fish#%d -- %dms\n", milliseconds, fish->fish_num, cuttingTime);

    int tmp=milliseconds;

    while(milliseconds-tmp<cuttingTime);//切魚中

    printf("%dms--Fish#%d: leaves CUTTER (complete 1st stage)\n", milliseconds, fish->fish_num);

    fish->cutted=1;//將魚標示為以切割

    pthread_mutex_lock(&cutQueueMutex);
    pthread_mutex_lock(&canQueueMutex);

        totalCuttingFactory++;

        if (cuttingQueue.count + canningQueue.count < tableSpace) {
            enqueue(&canningQueue, *fish);
            printf("%dms--Fish#%d: waiting in the slot (cutted)\n", milliseconds, fish->fish_num);//如果備料台沒滿，送到備料台

        }
        else{
            pthread_t freezerThreadl;
            pthread_create(&freezerThreadl, NULL, freezer, fish);//如果備料台滿了，送進冷凍庫

        }

    pthread_mutex_unlock(&canQueueMutex);
    pthread_mutex_unlock(&cutQueueMutex);


    sem_post(&cutSemaphore);//工廠使用結束
   



    free(arg);
    return NULL;
}

void* canningFactory(void* arg) {

    sem_wait(&canSemaphore);//用掉一間罐頭工廠
        int canningTime =  rand() % 51 + 50;

        pthread_mutex_lock(&canQueueMutex);//防止有人剛好在對canningQueue操作

            Fish fish;

            while (canningQueue.count <= 0) {//等待魚進來
                pthread_mutex_unlock(&canQueueMutex);
                    usleep(1000);//避免deadlock
                pthread_mutex_lock(&canQueueMutex);
            }

            totalCanningFactory--;

            fish = dequeue(&canningQueue);//取出魚

        pthread_mutex_unlock(&canQueueMutex);



        printf("%dms--Fish#%d: enters to the factory (CANNER)\n", milliseconds, fish.fish_num);

        printf("%dms--CANNER: processing & canning the Fish#%d -- %dms\n", milliseconds, fish.fish_num, canningTime);

        int tmp=milliseconds;

        while(milliseconds-tmp<canningTime);//做罐頭中

        printf("%dms--Fish#%d: leaves CANNER (Complete)\n", milliseconds, fish.fish_num);

        totalCanningFactory++;

    sem_post(&canSemaphore);//工廠使用結束

    free(arg);
    return NULL;
}

void* fishShip(void* arg) {

    int catchFishTime = rand() % 6 + 5;

    int tmp=milliseconds;

    while(milliseconds-tmp<catchFishTime);//捕魚中

    pthread_mutex_lock(&cutQueueMutex);
    pthread_mutex_lock(&canQueueMutex);

        Fish fishTmp;
        fishTmp.fish_num = fishNumber;
        fishTmp.cutted=0;

        Fish* fish = malloc(sizeof(Fish));
        *fish = fishTmp;

        if (cuttingQueue.count + canningQueue.count < tableSpace) {
            enqueue(&cuttingQueue, *fish);
            printf("%dms--Fish#%d: waiting in the slot\n",milliseconds, fish->fish_num);//如果備料台沒滿，送到備料台

        }
        else{

            pthread_t freezerThreadl;
            pthread_create(&freezerThreadl, NULL, freezer, fish);//如果備料台滿了，送進冷凍庫

        }
        fishNumber++;

    pthread_mutex_unlock(&canQueueMutex);
    pthread_mutex_unlock(&cutQueueMutex);

	free(arg);
    return NULL;
}


void* checkFactory(void* arg){

    int tmp=-10;
    while(1){
        int flag=0;

        if(milliseconds-tmp==10)//每10ms檢查一次
        {
            if(totalCuttingFactory>0){
                 printf("%dms--CUTTER: under maintenance.\n",milliseconds);

            }
            if(totalCanningFactory>0){
                 printf("%dms--CANNER: under maintenance.\n",milliseconds);

            }
            while(totalCuttingFactory>0&&totalCanningFactory>0)
            {

                if(milliseconds-tmp==10){
                    flag=1;
                    tmp=milliseconds;
                    printf("%dms--CUTTER: under reviewing together...\n",milliseconds);
                    printf("%dms--CANNER: under reviewing together...\n",milliseconds);

                }


            }
            if(!flag)
                tmp=milliseconds;

        }
       
    }

}


int main(int argc, char* argv[]) {
    //傳入參數
    srand(time(NULL));
    totalFish = atoi(argv[1]);//共有幾條魚
    tableSpace = atoi(argv[2]);//共有多少備料格
	totalCuttingFactory=atoi(argv[3]);//幾個切個工廠
    totalCanningFactory=atoi(argv[4]);//幾個罐頭工廠

    //初始化
    sem_init(&cutSemaphore, 0, totalCuttingFactory);
    sem_init(&canSemaphore, 0, totalCanningFactory);
    iniQueue(&cuttingQueue, tableSpace);
    iniQueue(&canningQueue, tableSpace);
    pthread_mutex_init(&cutQueueMutex, NULL);
    pthread_mutex_init(&canQueueMutex, NULL);

    pthread_t clockThread,checkFactoryThread,catchFishThread[totalFish],cuttingThread[totalFish], canningThread[totalFish];
    pthread_create(&checkFactoryThread, NULL, checkFactory, NULL);//工廠狀態確認
    pthread_create(&clockThread, NULL, timer, NULL);//計時器
    
    //切割和做成罐頭做的次數和魚數一樣，所以create和魚數一樣的thread，並等待魚進來且有工廠可以用
    for (int i = 0; i < totalFish; i++) {
        pthread_create(&cuttingThread[i], NULL, cuttingFactory, NULL);
        pthread_create(&canningThread[i], NULL, canningFactory, NULL);
    }
    
    //開始捕魚
    for (int i = 0; i < totalFish; i++) {
        pthread_create(&catchFishThread[i], NULL, fishShip, NULL);
        pthread_join(catchFishThread[i], NULL);
    }
    
    for (int i = 0; i < totalFish; i++) {
        pthread_join(cuttingThread[i], NULL);
    }
    totalCuttingFactory=-1;//表示所有魚都切完了
    
    for (int i = 0; i < totalFish; i++) {
        pthread_join(canningThread[i], NULL);
    }
    totalCanningFactory=-1;//表示所有魚都做成罐頭了了

    pthread_mutex_destroy(&cutQueueMutex);
    pthread_mutex_destroy(&canQueueMutex);
    sem_destroy(&cutSemaphore);
    sem_destroy(&canSemaphore);
    free(cuttingQueue.queue_data);
    free(canningQueue.queue_data);

    return 0;
}


