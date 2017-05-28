package org.androidtown.myapplication;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.zip.Inflater;


public class Recommend extends Fragment {// please change this Recommend

    private Context context;

    DataBase db;

    String[] naRecommendFoodList = {"배","바나나", "키위", "검은콩", "감자", "브로콜리", "양파", "고구마", "옥수수", "오이", "시금치", "호두"};
    int[] naRecommendKaList = {170, 358, 290, 1860, 396, 316, 146, 337, 287, 147, 558, 441,};
    double [] naRecommendAmountList = { 0.25, 0.5, 1, 1, 1, 0.5, 0.5, 0.5, 0.5, 0.5, 0.5, 10};
    String[] naUnitList={"개", "개", "개", "컵", "개", "개", "개", "개", "개", "개", "단", "알"}; //디비가 너무 귀찮았음... 흡 안귀찮을 때 디비로 바꿀게
    String[] cholRecommendFoodList = {"사과", "포도", "옥수수", "우유", "마늘", "부추", "양파", "표고버섯", "당근", "다시마", "아몬드", "굴", "감귤"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_recommend,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity().getApplicationContext();

        //초과량
        TextView naExceed = (TextView)view.findViewById(R.id.naExceedTxt);
        TextView cholExceed = (TextView)view.findViewById(R.id.cholExceedTxt);
        TextView fatExceed = (TextView)view.findViewById(R.id.fatExceedTxt);
        TextView sugarExceed = (TextView)view.findViewById(R.id.sugarExceedTxt);


        //추천음식/활동
        TextView naRecommend = (TextView)view.findViewById(R.id.naRecommend);
        TextView fatRecommend = (TextView)view.findViewById(R.id.fatRecommend);
        TextView cholRecommend = (TextView)view.findViewById(R.id.cholRecommend);
        TextView sugarRecommend = (TextView)view.findViewById(R.id.sugarRecommend);

        //추천음식/활동 양(얼마나?)
        TextView naAmount = (TextView)view.findViewById(R.id.naRecommendAmount);
        TextView fatAmount = (TextView)view.findViewById(R.id.fatRecommendAmount);
        TextView cholAmount = (TextView)view.findViewById(R.id.cholRecommendAmount);
        TextView sugarAmount = (TextView)view.findViewById(R.id.sugarRecommendAmount);

        String comment = "";// 코멘트 달 것. 임시

        db = MainActivity.getDBInstance();

        int na = (int) db.getNa();
        int chol = (int) db.getChol();
        int fat = (int) db.getFat();
        int sugar = (int) db.getSugar();

        if(na>2000)
        {
            int r = (int)(Math.random()*12);
            int excessAmount = na - 2000;

            naExceed.setText( "현재 권장량 2000mg 중 "+excessAmount+" mg 초과했습니다.");
            naRecommend.setText(naRecommendFoodList[r]+"을(를) 섭취하는 것을 추천합니다.\n");
            naRecommend.append( naRecommendFoodList[r]+"은(는) "+naRecommendAmountList[r]+" "+naUnitList[r]+" 당 "+naRecommendKaList[r]+" mg 만큼의 칼륨 함량을 가지고 있습니다.\n\n");
            naRecommend.append("WTO 에서는 나트륨과 칼륨을 1:1 비율로 섭취하는 것을 권장합니다.");

            double rate=1;
            if(excessAmount>naRecommendKaList[r])
            {
                rate= excessAmount/naRecommendKaList[r];
            }
            naAmount.setText("초과한 "+excessAmount+" mg 만큼의 칼륨을 섭취하려면 ");
            naAmount.append("약 "+rate*naRecommendAmountList[r]+" "+naUnitList[r]+"의 "+naRecommendFoodList[r]+"을(를) 섭취하는 것을 추천합니다.");
        }

        if(chol>600)
        {
            int r = (int)(Math.random()*13);

            int excessAmount = chol - 600;

            cholExceed.setText("현재 권장량 600mg 중 "+excessAmount+" mg 초과했습니다.");
            cholRecommend.setText( cholRecommendFoodList[r]+"을(를) 섭취하는 것을 추천합니다.");

        }
        if(fat>50)
        {
            int fatCal = 9;

            int walkingCal = 4 ;// 70kg 5km/h 기준
            int runningCal = 10; // 70kg 10km/h 기준
            int cycleCal = 8;// 18km/h 기준 fatsecret참고

            int excessAmount = fat - 50;
            int excessCalorie = excessAmount * fatCal;

            fatExceed.setText( "현재 권장량 50g 중 "+excessAmount+" g 초과했습니다.");
            fatRecommend.setText("\n"+excessAmount+" g 을(를) 소모하기 위해서는");
            fatRecommend.append(excessCalorie+" kcal 만큼 운동해야 합니다.");
            fatAmount.setText("걷기 (70kg 5km/h 기준): "+excessCalorie/walkingCal+" 분");
            fatAmount.append("\n달리기 (70kg 10km/h 기준) : "+excessCalorie/runningCal+" 분");
            fatAmount.append("\n자전거타기(70kg 18km/h 기준) : "+excessCalorie/cycleCal+" 분");
        }
        if(sugar>60)
        {
            int sugarCal = 4;

            int walkingCal = 4 ;// 70kg 5km/h 기준
            int runningCal = 10; // 70kg 10km/h 기준
            int cycleCal = 8;// 18km/h 기준 fatsecret참고

            int excessAmount = sugar - 60;
            int excessCalorie = excessAmount * sugarCal;

            sugarExceed.setText("현재 권장량 60g 중 "+excessAmount+" g 초과했습니다.");
            sugarRecommend.setText("\n\n"+excessAmount+" g 을(를) 소모하기 위해서는 ");
            sugarRecommend.append(excessCalorie+" kcal 만큼 운동해야 합니다.\n\n");
            sugarAmount.setText("걷기 (70kg 5km/h 기준): "+excessCalorie/walkingCal+" 분");
            sugarAmount.append("\n달리기 (70kg 10km/h 기준) : "+excessCalorie/runningCal+" 분");
            sugarAmount.append("\n자전거타기(70kg 18km/h 기준) : "+excessCalorie/cycleCal+" 분\n\n\n\n");

        }
    }

}
