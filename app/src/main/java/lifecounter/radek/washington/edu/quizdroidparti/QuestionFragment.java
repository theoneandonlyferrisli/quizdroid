package lifecounter.radek.washington.edu.quizdroidparti;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.zip.Inflater;


public class QuestionFragment extends Fragment {
    private Bundle qna;
    private int current;
    private String[] questions;
    private int[] answers;
    private String[] choices;

    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            qna = getArguments();
            questions = qna.getStringArray("Q");
            answers = qna.getIntArray("A");
            current = qna.getInt("current");
            if (current == 0)
                choices = qna.getStringArray("Q1");
            else
                choices = qna.getStringArray("Q2");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_question, container, false);

        // Layout controls
        final Button submit = (Button) view.findViewById(R.id.submit);
        submit.setVisibility(View.INVISIBLE);
        TextView question = (TextView) view.findViewById(R.id.question);
        question.setText(questions[current]);

        RadioGroup options = (RadioGroup)view.findViewById(R.id.choices);

        for (int i = 0; i < qna.getInt("numC"); i++) {
            String choice = choices[i];
            ((RadioButton) options.getChildAt(i)).setText(choice);
        }

        options.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton checked = (RadioButton) view.findViewById(checkedId);
                int selected = Integer.parseInt((String) checked.getTag());
                qna.putInt("selected", selected);
                qna.putInt("correctAnswer", answers[current]);
                qna.putInt("current", current);
                submit.setVisibility(View.VISIBLE);
            }
        });

        final AnswerFragment answerFragment = new AnswerFragment();
        answerFragment.setArguments(qna);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, answerFragment)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
