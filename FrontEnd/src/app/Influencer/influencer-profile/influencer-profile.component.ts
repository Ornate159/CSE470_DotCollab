import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service'; // Update the path
import { Router } from '@angular/router'; // Import the Router

@Component({
  selector: 'app-influencer-profile',
  templateUrl: './influencer-profile.component.html',
  styleUrls: ['./influencer-profile.component.css'],
})
export class InfluencerProfileComponent implements OnInit {
  user: any; // Adjust the type based on your user details

  constructor(private userService: UserService, private router: Router) {}

  ngOnInit(): void {
    this.userService.getUserProfile().subscribe(
      (data) => {
        this.user = data;
      },
      (error) => {
        console.error('Error fetching user profile:', error);
      }
    );
  }

  onEditProfileClick() {
    // Use the user's ID or any unique identifier to create a dynamic route
    // For example, assuming user.id is the unique identifier
    if (this.user && this.user.id) {
      this.router.navigate(['/influencer-profile-edit', this.user.id]);
    } else {
      console.error('User ID not found.');
    }
  }



  onChangePasswordClick() {
    const oldPassword = prompt('Enter your old password:');
    const newPassword = prompt('Enter your new password:');

    if (oldPassword && newPassword) {
      this.userService.changePassword(oldPassword, newPassword).subscribe(
        (response) => {
          console.log('Password changed successfully:', response);
          // Handle success (e.g., display a success message)
        },
        (error) => {
          console.error('Error changing password:', error);
          // Handle error (e.g., display an error message)
        }
      );
    }
  }
}