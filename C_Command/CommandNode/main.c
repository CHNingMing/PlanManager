#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main()
{
    time_t local_time;
    struct tm *uctTime;
    FILE *file;
    //存储计划项
    char nodeList[20];

    file = fopen("NodeInfo","r");
    if( file == NULL ){
        //初始化文件
        file = fopen("NodeInfo","w+");
        fprintf(file,"\n\n\n[NodeInfo]");
        fprintf(file,"\n\n[endNodeInfo]");
        printf("创建配置文件成功!\n");
    }
    #define a = 9;
    //读计划
    //存储计划
    char node_str[64][30];
    while( !feof(file) ){
        char t_char[30];
        fgets(t_char,sizeof(t_char),file);
        if( strncmp(t_char,"[NodeInfo]",strlen(t_char)-1) == 0 ){
            if( strlen(t_char) == 1 && t_char[0] == '\n' ){
                continue;
            }
            int index = 0;
            //定位到开始
            while( !feof(file) ){
                fgets(t_char,sizeof(t_char),file);
                if( strncmp(t_char,"[endNodeInfo]",strlen(t_char)) ){
                    if( strlen(t_char) == 1 && ( t_char[0] == '\n' || t_char[0] == ' ') ){
                        continue;
                    }
                }
                //添加到计划
                node_str[index] = t_char;
                printf("%c",node_str[index]);
                index++;
            }
        }
    }
    //printf("%s",node_str[0]);
//    for( int i = 0; i < sizeof(node_str); i++ ){
//            printf("%s",node_str[i]);
//    }


    fclose(file);


    time(&local_time);
    uctTime = gmtime(&local_time);

    int year = (uctTime -> tm_year)+1900;
    int money = (uctTime ->tm_mon)+1;
    int day = uctTime -> tm_mday;
    printf("\n============================\n");
    printf("%d-%d-%d\n",year,money,day);

    return 0;
}
