using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace CSharpBenchmark.infrastructure.socialmedia.netlog
{
    //Functional requirement 3.2.1
    //Test case 160: All classes in package infrastructure.socialmedia.netlog are not allowed to use modules in a higher layer 
    //Result: TRUE
    public class FriendsDAO
    {
        public FriendsDAO()
        {
            //FR5.2
            Console.WriteLine(SocialNetworkInfo.message);
        }
    }
}