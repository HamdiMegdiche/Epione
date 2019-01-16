namespace Epione.Domain
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("epione.complaint")]
    public partial class complaint
    {
        public int id { get; set; }

        public DateTime? date { get; set; }

        [StringLength(255)]
        public string description { get; set; }

        public int? patient_id { get; set; }

        public virtual user user { get; set; }
    }
}
