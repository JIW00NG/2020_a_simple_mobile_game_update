package hitesh.asimplegame;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import static hitesh.asimplegame.QuestionActivity.getLevel;
import static hitesh.asimplegame.GeneralResultActivity.getFirstScore;

/**
 * Implementation of App Widget functionality.
 */
public class RankingWidget extends AppWidgetProvider{

    private final static String ACTION_BTN = "ButtonClick";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ranking_widget);

        Intent intent = new Intent(context, RankingWidget.class).setAction(ACTION_BTN);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        views.setOnClickPendingIntent(R.id.reset_btn, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId,views);

        if(getLevel()=="easy"){
            views.setTextViewText(R.id.widget_easy_score, "E 1th Score : "+ getFirstScore(getLevel()));
        }else if(getLevel()=="normal"){
            views.setTextViewText(R.id.widget_normal_score, "N 1th Score : "+ getFirstScore(getLevel()));
        }else{
            views.setTextViewText(R.id.widget_hard_score, "H 1th Score : "+ getFirstScore(getLevel()));
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        if(action.equals(ACTION_BTN)){

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.ranking_widget);
            ComponentName componentName = new ComponentName(context, RankingWidget.class);

            if(getLevel()=="easy"){
                views.setTextViewText(R.id.widget_easy_score, "Easy 1th Score : "+ getFirstScore(getLevel()));
            }else if(getLevel()=="normal"){
                views.setTextViewText(R.id.widget_normal_score, "Normal 1th Score : "+ getFirstScore(getLevel()));
            }else{
                views.setTextViewText(R.id.widget_hard_score, "Hard 1th Score : "+ getFirstScore(getLevel()));
            }
            appWidgetManager.updateAppWidget(componentName, views);
        }
    }

}

