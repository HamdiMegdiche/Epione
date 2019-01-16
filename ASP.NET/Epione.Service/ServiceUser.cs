using Epione.Data.Infrastructure;
using Epione.Domain;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using ServicePattern;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;

namespace Epione.Service
{
    public class ServiceUser : Service<user>, IServiceUser
    {
        private static DatabaseFactory dbf = new DatabaseFactory();
        private static IUnitOfWork uof = new UnitOfWork(dbf);
        public ServiceUser() : base(uof)
        {


        }

        public async Task<user> getUserByIdAsync(int userId)
        {
            var request = new HttpRequestMessage(HttpMethod.Get,
         "http://localhost:8089/epione-web/user/"+userId.ToString());
            var client = new HttpClient();
            
            var response = await client.SendAsync(request);
            var byteArray = response.Content.ReadAsByteArrayAsync().Result;
            var result = Encoding.UTF8.GetString(byteArray, 0, byteArray.Length);
            var jsonObjects = JsonConvert.DeserializeObject<JObject>(result);
            var user = jsonObjects.Value<JObject>().ToObject<user>();
            return user;
        }

        public async Task<user> loginUserAsync(string EmailOrUsername, string password)
        {
            user currentUser = null;
            var request = new HttpRequestMessage(HttpMethod.Post,
         "http://localhost:8089/epione-web/user/authenticationUsername?username="+ EmailOrUsername.Trim()+"&password="+password.Trim());
            if (EmailOrUsername.Contains("@") && EmailOrUsername.Contains("."))
            {
                request = new HttpRequestMessage(HttpMethod.Post,
                    "http://localhost:8089/epione-web/user/authenticationEmail?email=" + EmailOrUsername.Trim() + "&password=" + password.Trim());
            }
            var client = new HttpClient();
            var response = await client.SendAsync(request);
            if(response.IsSuccessStatusCode)
            {
                var byteArray = response.Content.ReadAsByteArrayAsync().Result;
                var result = Encoding.UTF8.GetString(byteArray, 0, byteArray.Length);
               
                var Token = result.Trim();
                currentUser = await getUserByEmailAsync(EmailOrUsername);
            }
         
            return currentUser;
        }

        public async Task<user> getUserByEmailAsync(string EmailOrUsername)
        {
            var request = new HttpRequestMessage(HttpMethod.Get,
         "http://localhost:8089/epione-web/user/ByEmailOrUsername/" + EmailOrUsername.Trim());
            var client = new HttpClient();

            var response = await client.SendAsync(request);
            var byteArray = response.Content.ReadAsByteArrayAsync().Result;
            var result = Encoding.UTF8.GetString(byteArray, 0, byteArray.Length);
            var jsonObjects = JsonConvert.DeserializeObject<JObject>(result);
            var user = jsonObjects.Value<JObject>().ToObject<user>();
            return user;
        }

        public async Task<bool> RegisterUser(user user)
        {
            if (user.role.Equals("doctor"))
            {
                var request = new HttpRequestMessage(HttpMethod.Post,
       "http://localhost:8089/epione-web/user/addDoctor");
                var client = new HttpClient();
                var json = JsonConvert.SerializeObject(user);
                request.Content = new StringContent(json, Encoding.UTF8, "application/json");
                var response = await client.SendAsync(request);
                if (response.IsSuccessStatusCode)
                {
                    return await Task.FromResult(true);
                   
                }
                return await Task.FromResult(false);
            }
            else
            {
                var request = new HttpRequestMessage(HttpMethod.Post,
      "http://localhost:8089/epione-web/user/addPatient");
                var client = new HttpClient();
                request.Content = new StringContent(JsonConvert.SerializeObject(user), Encoding.UTF8, "application/json");
                var response = await client.SendAsync(request);
                if (response.IsSuccessStatusCode)
                {
                    return await Task.FromResult(true);

                }
                return await Task.FromResult(false);
            }

        }

        public async Task<HttpResponseMessage> LogOff(int userId)
        {
      
                var request = new HttpRequestMessage(HttpMethod.Post,
                    "http://localhost:8089/epione-web/user/logoff/"+userId);
                var client = new HttpClient();
            return await client.SendAsync(request);
        }


    }
}
