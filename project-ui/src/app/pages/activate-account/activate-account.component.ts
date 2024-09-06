import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/services';
import { CommonModule } from '@angular/common';
import { CodeInputModule } from 'angular-code-input';

@Component({
  selector: 'app-activate-account',
  standalone: true,
  imports: [CommonModule, CodeInputModule],
  templateUrl: './activate-account.component.html',
  styleUrl: './activate-account.component.scss',
})
export class ActivateAccountComponent {
  message = '';
  isOkay = true;
  submitted = false;

  constructor(
    private router: Router,
    private authService: AuthenticationService
  ) {}

  redirectToLogin() {
    this.router.navigate(['login']);
  }
  onCodeCompleted(token: string) {
    this.confirmAccount(token);
  }
  confirmAccount(token: string) {
    this.authService.confirm({ token }).subscribe({
      next: () => {
        this.message =
          'Your Account has been successfully activated.\nNow you can proceed to the Login';
        this.submitted = true;
        this.isOkay = true;
      },
      error: () => {
        this.message = 'Token has been expired or invalid';
        this.submitted = false;
        this.isOkay = false;
      },
    });
  }
}
