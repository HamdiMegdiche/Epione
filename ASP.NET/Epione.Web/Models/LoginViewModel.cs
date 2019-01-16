using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Epione.Web.Models
{
    public class LoginViewModel
    {
        [Required(ErrorMessage = "Email/username is required")]
        [Display(Name = "Email")]
        //[EmailAddress]
        public string Email { get; set; }

        [Required(ErrorMessage = "Password is required")]
        [DataType(DataType.Password)]
        [Display(Name = "Password")]
        public string Password { get; set; }

    }
}