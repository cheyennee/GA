package com.example.ga;
import java.util.Random;
public class GA {
    public int generation;
    public int cur_best;
    public genotype[] population = new genotype[Constant.POPSIZE + 1];
    public genotype[] newpopulation = new genotype[Constant.POPSIZE + 1];
    private Random random = new Random(1000);
    public GA() {
        for(int n = 0;n<Constant.POPSIZE + 1;n++) {
            population[n] = new genotype();
            newpopulation[n] = new genotype();
        }
    }
    public  double randval(int low,int high) {
        double val;
        val = random.nextInt(high-low+1) + low;
        return val;
    }
    public void initialize() {
        int i,j;
        int lbound,ubound;
        for(i=0;i<Constant.NVARS;i++) {
            lbound = Constant.details.get(i).getLbound();
            ubound = Constant.details.get(i).getUbound();
            for(j=0;j<Constant.POPSIZE;j++) {
                population[j].fitness = 0;
                population[j].rfitness=0;
                population[j].cfitness=0;
                population[j].lower[i]=lbound;
                population[j].upper[i]=ubound;
                population[j].gene[i]=randval(population[j].lower[i],population[j].upper[i]);
            }
        }
    }
    public void evaluate() {
        int mem;
        int i;
        double[] x = new double [Constant.NVARS+1];
        int n;

        for(mem=0;mem<Constant.POPSIZE;mem++){
            for(i=0;i<Constant.NVARS;i++)
                x[i+1]=population[mem].gene[i];
            for(n=1;n<=Constant.NVARS;n++)
                population[mem].fitness += Constant.details.get(n-1).ratio*Math.pow(x[n], Constant.details.get(n-1).power);
            //population[mem].fitness=x[1]*x[1]-x[1]*x[2]+10;
        }
        if(!Constant.isMax){
            population[mem].fitness = - population[mem].fitness;
        }
    }
    public void keep_the_best() {
        int mem;
        int i;
        cur_best=0;
        for(mem=0;mem<Constant.POPSIZE;mem++){
            if(population[mem].fitness>population[Constant.POPSIZE].fitness){
                cur_best=mem;
                population[Constant.POPSIZE].fitness=population[mem].fitness;
            }
        }
        for(i=0;i<Constant.NVARS;i++)
            population[Constant.POPSIZE].gene[i]=population[cur_best].gene[i];
    }
    public void elitist() {
        int i;
        double best,worst;
        int best_mem = 0,worst_mem = 0;
        best=population[0].fitness;
        worst=population[0].fitness;
        for(i=0;i<Constant.POPSIZE;i++){
            if(population[i].fitness>population[i+1].fitness){
                if(population[i].fitness>=best){
                    best=population[i].fitness;
                    best_mem=i;
                }
                if(population[i+1].fitness<=worst){
                    worst=population[i+1].fitness;
                    worst_mem=i+1;
                }
            }else{
                if(population[i].fitness<=worst){
                    worst=population[i].fitness;
                    worst_mem=i;
                }
                if(population[i].fitness>=best){
                    best=population[i+1].fitness;
                    best_mem=i+1;
                }
            }
        }
        if(best>=population[Constant.POPSIZE].fitness){
            for(i=0;i<Constant.NVARS;i++)
                population[Constant.POPSIZE].gene[i]=population[best_mem].gene[i];
            population[Constant.POPSIZE].fitness=population[best_mem].fitness;
        }else{
            for(i=0;i<Constant.NVARS;i++)
                population[worst_mem].gene[i]=population[Constant.POPSIZE].gene[i];
            population[worst_mem].fitness=population[Constant.POPSIZE].fitness;
        }
    }
    public void select() {
        int mem,i,j;
        double sum=0;
        double p;
        for(mem=0;mem<Constant.POPSIZE;mem++)
            sum+=population[mem].fitness;
        for(mem=0;mem<Constant.POPSIZE;mem++)
            population[mem].rfitness=population[mem].fitness/sum;//选择概率
        population[0].cfitness=population[0].rfitness;
        for(mem=1;mem<Constant.POPSIZE;mem++)
            population[mem].cfitness=population[mem-1].cfitness+population[mem].rfitness;
        for(i=0;i<Constant.POPSIZE;i++){
            p=random.nextDouble()%1000/1000.0;
            if(p<population[0].cfitness)
                newpopulation[i]=population[0];
            else{
                for(j=0;j<Constant.POPSIZE;j++)
                    if(p>=population[j].cfitness&&p<population[j+1].cfitness)
                        newpopulation[i]=population[j+1];
            }
        }
        for(i=0;i<Constant.POPSIZE;i++)
            population[i]=newpopulation[i];
    }
    public void Xover(int one,int two){
        int i;
        int point;
        double temp;
        if(Constant.NVARS>1){
            if(Constant.NVARS==2)
                point=1;
            else
                point=(int) ((random.nextInt()%(Constant.NVARS-1))+1);
            for(i=0;i<point;i++) {
                temp = population[one].gene[i];
                population[one].gene[i] = population[two].gene[i];
                population[two].gene[i] = temp;
            }
        }
    }
    public void crossover(){
        int mem,one = 0;
        int first=0;
        double x;
        for(mem=0;mem<Constant.POPSIZE;++mem){
            x=random.nextDouble()%1000/1000.0;
            if(x<Constant.PXOVER){
                ++first;
                if(first%2==0)
                    Xover(one,mem);
                else
                    one=mem;
            }
        }
    }
    public void mutate(){
        int i,j;
        int lbound,hbound;
        double x;
        for(i=0;i<Constant.POPSIZE;i++)
            for(j=0;j<Constant.NVARS;j++){
                x=random.nextDouble()%1000/1000.0;
                if(x<Constant.PMUTATION){
                    lbound=population[i].lower[j];
                    hbound=population[i].upper[j];
                    population[i].gene[j]=randval(lbound,hbound);
                }
            }
    }

}
