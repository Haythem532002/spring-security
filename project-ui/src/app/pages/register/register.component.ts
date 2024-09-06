import { Component } from '@angular/core';
import { RegistrationRequest } from '../../services/models';
import { AuthenticationService } from '../../services/services';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  regRequest: RegistrationRequest = {
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  };
  errorMsg: Array<string> = [];
  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}

  login() {
    this.router.navigate(['login']);
  }
  register() {
    this.errorMsg = [];
    this.authService.register({ body: this.regRequest }).subscribe({
      next: (res) => {
        this.router.navigate(['/activate-account']);
      },
      error: (err) => {
        console.log(err);
        if (err.error.validationErrors) {
          this.errorMsg = err.error.validationErrors;
        } else {
          this.errorMsg.push(err.error.error);
        }
      },
    });
  }
}
