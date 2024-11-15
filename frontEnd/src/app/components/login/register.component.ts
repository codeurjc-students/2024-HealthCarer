import { Component } from '@angular/core';
import { LoginService } from '../../services/login.service';

@Component({
    selector: 'register',
    templateUrl: './register.component.html'
})
export class RegisterComponent {

    constructor(public loginService: LoginService) { }

    register(event: any, user: string, email:string, pass: string) {

        event.preventDefault();

        this.loginService.register(user, email, pass);
    }

}
