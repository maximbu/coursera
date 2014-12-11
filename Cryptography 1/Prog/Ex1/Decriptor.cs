using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Test1
{
    public class Decriptor
    {
        private const string cipher1 = "315c4eeaa8b5f8aaf9174145bf43e1784b8fa00dc71d885a804e5ee9fa40b16349c146fb778cdf2d3aff021dfff5b403b510d0d0455468aeb98622b137dae857553ccd8883a7bc37520e06e515d22c954eba5025b8cc57ee59418ce7dc6bc41556bdb36bbca3e8774301fbcaa3b83b220809560987815f65286764703de0f3d524400a19b159610b11ef3e";

        private const string cipher2 = "234c02ecbbfbafa3ed18510abd11fa724fcda2018a1a8342cf064bbde548b12b07df44ba7191d9606ef4081ffde5ad46a5069d9f7f543bedb9c861bf29c7e205132eda9382b0bc2c5c4b45f919cf3a9f1cb74151f6d551f4480c82b2cb24cc5b028aa76eb7b4ab24171ab3cdadb8356f";

        private const string cipher3 = "32510ba9a7b2bba9b8005d43a304b5714cc0bb0c8a34884dd91304b8ad40b62b07df44ba6e9d8a2368e51d04e0e7b207b70b9b8261112bacb6c866a232dfe257527dc29398f5f3251a0d47e503c66e935de81230b59b7afb5f41afa8d661cb";

        private const string cipher4 = "32510ba9aab2a8a4fd06414fb517b5605cc0aa0dc91a8908c2064ba8ad5ea06a029056f47a8ad3306ef5021eafe1ac01a81197847a5c68a1b78769a37bc8f4575432c198ccb4ef63590256e305cd3a9544ee4160ead45aef520489e7da7d835402bca670bda8eb775200b8dabbba246b130f040d8ec6447e2c767f3d30ed81ea2e4c1404e1315a1010e7229be6636aaa";

        private const string cipher5 = "3f561ba9adb4b6ebec54424ba317b564418fac0dd35f8c08d31a1fe9e24fe56808c213f17c81d9607cee021dafe1e001b21ade877a5e68bea88d61b93ac5ee0d562e8e9582f5ef375f0a4ae20ed86e935de81230b59b73fb4302cd95d770c65b40aaa065f2a5e33a5a0bb5dcaba43722130f042f8ec85b7c2070";

        private const string cipher6 = "32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd2061bbde24eb76a19d84aba34d8de287be84d07e7e9a30ee714979c7e1123a8bd9822a33ecaf512472e8e8f8db3f9635c1949e640c621854eba0d79eccf52ff111284b4cc61d11902aebc66f2b2e436434eacc0aba938220b084800c2ca4e693522643573b2c4ce35050b0cf774201f0fe52ac9f26d71b6cf61a711cc229f77ace7aa88a2f19983122b11be87a59c355d25f8e4";

        private const string cipher7 = "32510bfbacfbb9befd54415da243e1695ecabd58c519cd4bd90f1fa6ea5ba47b01c909ba7696cf606ef40c04afe1ac0aa8148dd066592ded9f8774b529c7ea125d298e8883f5e9305f4b44f915cb2bd05af51373fd9b4af511039fa2d96f83414aaaf261bda2e97b170fb5cce2a53e675c154c0d9681596934777e2275b381ce2e40582afe67650b13e72287ff2270abcf73bb028932836fbdecfecee0a3b894473c1bbeb6b4913a536ce4f9b13f1efff71ea313c8661dd9a4ce";

        private const string cipher8 = "315c4eeaa8b5f8bffd11155ea506b56041c6a00c8a08854dd21a4bbde54ce56801d943ba708b8a3574f40c00fff9e00fa1439fd0654327a3bfc860b92f89ee04132ecb9298f5fd2d5e4b45e40ecc3b9d59e9417df7c95bba410e9aa2ca24c5474da2f276baa3ac325918b2daada43d6712150441c2e04f6565517f317da9d3";

        private const string cipher9 = "271946f9bbb2aeadec111841a81abc300ecaa01bd8069d5cc91005e9fe4aad6e04d513e96d99de2569bc5e50eeeca709b50a8a987f4264edb6896fb537d0a716132ddc938fb0f836480e06ed0fcd6e9759f40462f9cf57f4564186a2c1778f1543efa270bda5e933421cbe88a4a52222190f471e9bd15f652b653b7071aec59a2705081ffe72651d08f822c9ed6d76e48b63ab15d0208573a7eef027";

        private const string cipher10 = "466d06ece998b7a2fb1d464fed2ced7641ddaa3cc31c9941cf110abbf409ed39598005b3399ccfafb61d0315fca0a314be138a9f32503bedac8067f03adbf3575c3b8edc9ba7f537530541ab0f9f3cd04ff50d66f1d559ba520e89a2cb2a83";

        private const string targetCipher = "32510ba9babebbbefd001547a810e67149caee11d945cd7fc81a05e9f85aac650e9052ba6a8cd8257bf14d13e6f0a803b54fde9e77472dbff89d71b57bddef121336cb85ccb8f3315f4b52e301d16e9f52f904";

        private const string test1 = "6d6178696d20697320746865206b696e67";//"maxim is the king";
        private const string test2 = "4920616d2068756e677279";//"I am hungry";
        private const string test3 = "6c65747320656174207375736869";//"lets eat sushi";

        private static readonly List<string> ciphers = new List<string>() ;
        private static readonly List<string> guesses = new List<string>();

        static Decriptor()
        {
            ciphers.Add(HexToString(cipher1));
            ciphers.Add(HexToString(cipher2));
            ciphers.Add(HexToString(cipher3));
            ciphers.Add(HexToString(cipher4));
            ciphers.Add(HexToString(cipher5));
            ciphers.Add(HexToString(cipher6));
            ciphers.Add(HexToString(cipher7));
            ciphers.Add(HexToString(cipher8));
            ciphers.Add(HexToString(cipher9));
            ciphers.Add(HexToString(cipher10));
            ciphers.Add(HexToString(targetCipher));

            //ciphers.Add(HexToString(test1));
            //ciphers.Add(HexToString(test2));
            //ciphers.Add(HexToString(test3));
            
            //guesses.Add(" the ");
           // guesses.Add(" message ");
            //guesses.Add(" steal ");
           // guesses.Add(" probably ");
           // guesses.Add(" two types ");
           // guesses.Add(" the point ");
            //guesses.Add(" anything about ");
            // guesses.Add(" next produced ");
            // guesses.Add(" solving code ");
            // guesses.Add(" something about ");
            //guesses.Add(" generating ");
            // guesses.Add(" ciphertext produced by ");
             //guesses.Add(" OxfordDictionary ");
            // guesses.Add(" factor the number ");
            // guesses.Add(" from your little ");
            //guesses.Add("The secret message ");
            //guesses.Add("There are two main types of cryptography");
            //guesses.Add("We can factor the number ");
            //guesses.Add("The ciphertext produced by ");
            //guesses.Add("The secret message is: When");
            //guesses.Add("The aocise OxfordDictionary ");
           // guesses.Add("A (private-key)  encryption and (public-key) ");
            //guesses.Add("Euler would probably enjoyed ");
            //guesses.Add("There are two types of cryptography ");
            //guesses.Add("The ciphertext produced by a weak encryption ");
           // guesses.Add("The secret message is: When using a stream cipher ");
            //guesses.Add("The ciphertext produced by a weak encryption algorithm ");
            //guesses.Add("You don't want to buy a set of car keys from a guy who specializes in stealing ");
            //guesses.Add("Euler would probably enjoy that now his theorem becomes ");
            //guesses.Add("The secret message is: When using a stream cipher, never ");
            guesses.Add("The secret message is: When using a stream cipher, never use the key more than once");



            // var a = StringToHex(" anything about ");
        }


        public static string StringToHex(string input)
        {
            char[] values = input.ToCharArray();
            var stringBuilder = new StringBuilder();
            foreach (char letter in values)
            {
                // Get the integral value of the character. 
                int value = Convert.ToInt32(letter);
                // Convert the decimal value to a hexadecimal value in string form. 
                stringBuilder.Append(String.Format("{0:x}", value));
            }
            return stringBuilder.ToString();
        }

        public static string HexToString(string hex)
        {
            var sb = new StringBuilder();

            for (int i = 0; i <= hex.Length - 2; i += 2)
            {

                sb.Append(Convert.ToString(Convert.ToChar(Int32.Parse(hex.Substring(i, 2),

                                                                      System.Globalization.NumberStyles.HexNumber))));

            }

            return sb.ToString();
        }

        //public static string CalculateKey()
        //{
        //    TryDecrypt(HexToString(cipher9));
        //    return "";
        //}

        public static string CalculateKey()
        {
            foreach (var decripted in ciphers.Select(TryDecrypt))
            {
                Console.WriteLine("Decripted text is :" + decripted);
            }
            return "";
        }

        public static string CalculateKey_new()
        {
            var xors = new string[ciphers.Count, ciphers.Count];

            for (int i = 0; i < ciphers.Count; i++)
            {
                var letters = new char[ciphers[i].Length];
                Console.WriteLine("Cipher {0} :" , i);
                for (int j = 0; i!= j && j < ciphers.Count; j++)
                {
                    var tmp = XorTwoCiphers(ciphers[i], ciphers[j]);
                    xors[i, j] = XorSpaces(tmp, true);
                    Addletters(XorSpaces(tmp, true), letters);

                }
                Console.WriteLine(letters);               
            }
            

            //foreach (var decripted in ciphers.Select(TryDecrypt))
            //{
            //    Console.WriteLine("Decripted text is :" + decripted);
            //}
            return "";
        }

        private static void Addletters(string xor, char[] letters)
        {
            for (int i = 0; i < xor.Length; i++)
            {
                if (xor[i] != '?')
                {
                    letters[i] = xor[i];
                }
            }
        }

        private static string XorSpaces(string tmp,bool lettersOnly)
        {
            byte[] b2 = Encoding.Default.GetBytes(tmp);
            var b3 = new byte[b2.Length];
            for (int i = 0; i < tmp.Length; i++)
            {
                b3[i] = (byte) (tmp[i] ^ ' ');

                if (lettersOnly && !Char.IsLetter((char)b3[i]))
                {
                    b3[i] = (byte)'?';
                }
            }
            return Encoding.Default.GetString(b3);
        }

        public static string CalculateKey2()
        {
            foreach (var cipher in ciphers)
            {
                var m1 = new char[Encoding.Default.GetBytes(cipher).Length];
                FindSpaces(cipher, m1);
                Console.WriteLine("Decripted text is :" + new string(m1));
            }
            return "";
        }



        public static string TryDecrypt(string cipher)
        {         
            var ops = new List<Tuple<string,int>> ();

            char[] m = null;

            foreach (var guess in guesses)
            {
               m = TryGuess(cipher, ops, guess);
            }

            ops.Sort();
            ops = ops.Distinct().Select(x=> new Tuple<string,int> (x.Item1.Replace(" ","_"),x.Item2)).ToList();
            Console.WriteLine("For string indexed : " + ciphers.IndexOf(cipher));
           // ops.ForEach(Console.WriteLine);

            return new string(m);
        }

        private static char[] TryGuess(string cipher, List<Tuple<string,int>> ops, string guess)
        {
            var m1 = new char[Encoding.Default.GetBytes(cipher).Length];
            foreach (string c in ciphers)
            {
                if (c != cipher)
                {
                    string xor = XorTwoCiphers(cipher, c);
                    //var len = Math.Min(cipher.Length, c.Length);
                    //var words = MakeGuess(xor, guess, len);
                    //ops.AddRange(words.Select(x => new Tuple<string, int>(x, ciphers.IndexOf(c) + 1)));
                    var words = XorTwoCiphers(xor.Substring(0,guess.Length), guess);
                    Console.WriteLine(words);                    
                }
            }
            return m1;
        }

        private static void DecryptCipher(string c1, char[] m1, string xor)
        {
            FindSpaces(c1, m1);
            FillLetters(c1, m1, xor);
        }

        private static IEnumerable<string> MakeGuess(string xor, string guess, int length)
        {
            var ops = new List<string>();

            for (int i = 0; i < length - guess.Length; i++)
            {
                var output = XorTwoCiphers(xor.Substring(i, guess.Length), guess);
                if (output.All(x => Char.IsLetter(x) || x == ' '))
                {
                    if (output != guess)
                    {
                        ops.Add(output);
                    }
                }
            }
            return ops;
        }

        private static void FillLetters(string c1, char[] m1, string xor)
        {
            byte[] b1 = Encoding.Default.GetBytes(c1);

            for (int i = 0; i < b1.Length; i++)
            {
                if (Char.IsLetter(m1[i]) || m1[i] == ' ')
                    continue;

                if (Char.IsLetter(xor[i]))
                {
                    m1[i] = Char.IsUpper(xor[i]) ? Char.ToLower(xor[i]) : Char.ToUpper(xor[i]);
                }
            }
        }


        private static void FindSpaces(string c1, char[] m1)
        {
            var spaceCnt = new int[m1.Length];
            foreach (string c in ciphers)
            {
                string xor = XorTwoCiphers(c1, c);
                for (int i = 0; i < m1.Length; i++)
                {
                    if (Char.IsLetter(xor[i]))
                    {
                        spaceCnt[i]++;
                    }
                }
            }

             for (int i = 0; i < m1.Length; i++)
             {
                 if(spaceCnt[i] >= ciphers.Count-3)
                 {
                     m1[i] = ' ';
                 }
                 else
                 {
                     m1[i] = 'X';
                 }
             }
            
        }


        public static string XorTwoCiphers (string s1 , string s2)
        {
            byte[] b1 = Encoding.Default.GetBytes(s1);
            byte[] b2 = Encoding.Default.GetBytes(s2);
            int len = Math.Min(b1.Length, b2.Length);
            var b3 = new byte[Math.Min(b1.Length, b2.Length)];

            for (int i = 0; i < len; i++)
            {
                b3[i] = (byte) (b1[i] ^ b2[i]);
            }
            return Encoding.Default.GetString(b3);
        }

        public static string DecryptMessage(string key)
        {
            return XorTwoCiphers(key, targetCipher);
        }
    }
}
