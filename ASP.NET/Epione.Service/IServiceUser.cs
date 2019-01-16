using ServicePattern;
using Epione.Domain;
using System.Threading.Tasks;
using System.Net.Http;

namespace Epione.Service
{
    public interface IServiceUser : IService<user>
    {

         Task<user> getUserByIdAsync(int userId);
         Task<user> loginUserAsync(string userNameOrEmail, string password);
         Task<user> getUserByEmailAsync(string email);
         Task<bool> RegisterUser(user user);
         Task<HttpResponseMessage> LogOff(int userId);
    }
}