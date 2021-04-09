package br.com.mac.projetoacacia.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import br.com.mac.projetoacacia.BR;
import br.com.mac.projetoacacia.R;
import br.com.mac.projetoacacia.databinding.LoginLayoutBinding;
import br.com.mac.projetoacacia.model.Usuario;
import br.com.mac.projetoacacia.repository.LoginRepository;
import br.com.mac.projetoacacia.ui.viewmodel.LoginViewModelFactory;
import br.com.mac.projetoacacia.ui.viewmodel.LoginViewmodel;
import br.com.mac.projetoacacia.utils.Utils;

public class LoginActivity extends AppCompatActivity {
    private LoginViewmodel _viewModel;
    private Usuario _user;
    private LoginLayoutBinding _viewDataBinding ;
    private ProgressBar _loadingProgressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _viewDataBinding = DataBindingUtil.setContentView(this, R.layout.login_layout);
        setTitle(getString(R.string.login));
        init();
    }

    private void init() {

       _loadingProgressBar = findViewById(R.id.login_loading);
        _user = new Usuario();
        _viewDataBinding.setVariable(BR.usuario, _user);
        _viewDataBinding.loginBtnCadastro.setOnClickListener(cadClickListener);
        _viewDataBinding.loginBtnLogin.setOnClickListener(loginClickListener);

        _viewModel = new ViewModelProvider(this, new LoginViewModelFactory(new LoginRepository(this))).get(LoginViewmodel.class);
    }

    public final View.OnClickListener loginClickListener = view -> {
        if (isNotValidAction())
            return;

        view.setEnabled(false);
        validate(_user, (Button) view, _loadingProgressBar);
    };

    private boolean isNotValidAction() {
        if(!Utils.isConnected(LoginActivity.this)) {
            Utils.showToast(LoginActivity.this, "É necessário estar conectado a internet!");
            return true;
        }

        if(!(_user.username.length() > 3 && _user.password.length() > 3)){
            Utils.showToast(LoginActivity.this, "Preencha o login(3) e senha(3)!");
            return true;
        }
        return false;
    }

    public final View.OnClickListener cadClickListener = view -> {
        if (isNotValidAction())
            return;

        view.setEnabled(false);
        registerUser(_user,(Button)view, _loadingProgressBar);
    };

    private void registerUser(final Usuario user, final Button button, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        _viewModel.registerUser(user, (sucess, message) -> {
            Utils.showToast(LoginActivity.this, message);
            progressBar.setVisibility(View.GONE);
            button.setEnabled(true);
            return sucess;
        });
    }

    private void validate(final Usuario user, final Button button, final ProgressBar progressBar) {
        progressBar.setVisibility(View.VISIBLE);
        _viewModel.validadeAccess(user, (sucess, message, userRet) -> {
            if(sucess){
                final Intent intent = new Intent(getApplicationContext(), NotasActivity.class);
                intent.putExtra("id",userRet.getUserId());
                intent.putExtra("token", userRet.getId());
                startActivity(intent);
            }else{
                String msg = _viewModel.getMessage(message);
                Utils.showToast(LoginActivity.this, msg);
            }
            progressBar.setVisibility(View.GONE);
            button.setEnabled(true);
            return sucess;
        });
    }
}